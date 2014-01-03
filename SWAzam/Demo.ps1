param([String]$defaultMP3s = "")

$scriptDirectory = split-path -parent $MyInvocation.MyCommand.Definition
$configBaseDirectory = "C:\\temp\\SWAzam"

$HSQLDBproperties = @"
#HSQL Database Engine 2.3.0
#Sat Dec 21 20:59:34 CET 2013
version=2.3.0
modified=no
"@

$HSQLDBscriptTemplate = @"
SET DATABASE UNIQUE NAME @@NAME_PLACEHOLDER@@
SET DATABASE GC 0
SET DATABASE DEFAULT RESULT MEMORY ROWS 0
SET DATABASE EVENT LOG LEVEL 0
SET DATABASE SQL NAMES FALSE
SET DATABASE SQL REFERENCES FALSE
SET DATABASE SQL SIZE TRUE
SET DATABASE SQL TYPES FALSE
SET DATABASE SQL TDC DELETE TRUE
SET DATABASE SQL TDC UPDATE TRUE
SET DATABASE SQL TRANSLATE TTI TYPES TRUE
SET DATABASE SQL CONCAT NULLS TRUE
SET DATABASE SQL UNIQUE NULLS TRUE
SET DATABASE SQL CONVERT TRUNCATE TRUE
SET DATABASE SQL AVG SCALE 0
SET DATABASE SQL DOUBLE NAN TRUE
SET DATABASE TRANSACTION CONTROL LOCKS
SET DATABASE DEFAULT ISOLATION LEVEL READ COMMITTED
SET DATABASE TRANSACTION ROLLBACK ON CONFLICT TRUE
SET DATABASE TEXT TABLE DEFAULTS ''
SET FILES WRITE DELAY 500 MILLIS
SET FILES BACKUP INCREMENT TRUE
SET FILES CACHE SIZE 10000
SET FILES CACHE ROWS 50000
SET FILES SCALE 32
SET FILES LOB SCALE 32
SET FILES DEFRAG 0
SET FILES NIO TRUE
SET FILES NIO SIZE 256
SET FILES LOG TRUE
SET FILES LOG SIZE 50
CREATE USER SA PASSWORD DIGEST 'd41d8cd98f00b204e9800998ecf8427e'
ALTER USER SA SET LOCAL TRUE
CREATE SCHEMA PUBLIC AUTHORIZATION DBA
SET SCHEMA PUBLIC
@@SCHEMA_PLACEHOLDER@@
ALTER SEQUENCE SYSTEM_LOBS.LOB_ID RESTART WITH 1
SET DATABASE DEFAULT INITIAL SCHEMA PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.SQL_IDENTIFIER TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.YES_OR_NO TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.TIME_STAMP TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.CARDINAL_NUMBER TO PUBLIC
GRANT USAGE ON DOMAIN INFORMATION_SCHEMA.CHARACTER_DATA TO PUBLIC
GRANT DBA TO SA
SET SCHEMA SYSTEM_LOBS
INSERT INTO BLOCKS VALUES(0,2147483647,0)
SET SCHEMA PUBLIC
@@TESTDATA_PLACEHOLDER@@
"@

function CompileServer()
{
	Write-Host "Compiling Server."
	$mavenArguments = @('clean', 'compile')
	Start-Process "mvn" $mavenArguments -WorkingDirectory "$scriptDirectory\server" -NoNewWindow -Wait
	Write-Host "Server compilation completed." -ForegroundColor Green
}

function CompileClient()
{
	Write-Host "Compiling Client."
	$mavenArguments = @('clean', 'compile')
	Start-Process "mvn" $mavenArguments -WorkingDirectory "$scriptDirectory\client" -NoNewWindow -Wait
	Write-Host "Client compilation completed." -ForegroundColor Green
}

function CompilePeer()
{
	Write-Host "Compiling Peer."
	$mavenArguments = @('clean', 'compile')
	Start-Process "mvn" $mavenArguments -WorkingDirectory "$scriptDirectory\peer" -NoNewWindow -Wait
	Write-Host "Peer compilation completed." -ForegroundColor Green
}

function StartServer([string[]] $arguments)
{
	Write-Host "Starting Server..."
	$DexecArgs = $arguments -Join ' '
	$mavenArguments = @("exec:java", "-Dexec.args=""$DexecArgs""")
	Start-Process "mvn" $mavenArguments -WorkingDirectory "$scriptDirectory\server"
	Write-Host "Server has been started in external console." -ForegroundColor Green
}

function StartClient([string[]] $arguments)
{
	Write-Host "Starting Client...."
	$DexecArgs = $arguments -Join ' '
	$mavenArguments = @("exec:java", "-Dexec.args=""$DexecArgs""")
	Start-Process "mvn" $mavenArguments -WorkingDirectory ".\client"
	Write-Host "Client has been started in external console." -ForegroundColor Green
}

function StartPeer([string[]] $arguments)
{
	Write-Host "Starting Peer..."
	$DexecArgs = $arguments -Join ' '
	$mavenArguments = @("exec:java", "-Dexec.args=""$DexecArgs""")
	Start-Process "mvn" $mavenArguments -WorkingDirectory ".\peer"
	Write-Host "Peer has been started in external console." -ForegroundColor Green
}

function CreateConfigDirectories()
{
	$configBaseDirectoryExists = Test-Path $configBaseDirectory
	If ($configBaseDirectoryExists -eq $true){
		Remove-Item $configBaseDirectory -Force -Recurse | Out-Null
	}
	New-Item $configBaseDirectory -type Directory -Force | Out-Null
	New-Item "$configBaseDirectory\\peer1" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer1\\mp3s" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer1\\database" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer2" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer2\\mp3s" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer2\\database" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer3" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer3\\mp3s" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\peer3\\database" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\client1" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\client1\\database" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\client2" -type Directory | Out-Null
	New-Item "$configBaseDirectory\\client2\\database" -type Directory | Out-Null
	
	if($defaultMP3s -ne "")
	{
		Copy-Item $defaultMP3s\\* "$configBaseDirectory\\peer1\\mp3s"
		Copy-Item $defaultMP3s\\* "$configBaseDirectory\\peer2\\mp3s"
		Copy-Item $defaultMP3s\\* "$configBaseDirectory\\peer3\\mp3s"
	}
	
	Write-Host "Configuration directories have been created at $configBaseDirectory." -ForegroundColor Green
}

function CreateDatabase([string] $databaseFolder, [string] $databaseName, [string] $schema, [string] $testData)
{
	$HSQLDBscript = $HSQLDBscriptTemplate.Replace("@@NAME_PLACEHOLDER@@", $databaseName)
	$HSQLDBscript = $HSQLDBscript.Replace("@@SCHEMA_PLACEHOLDER@@", $schema)
	$HSQLDBscript = $HSQLDBscript.Replace("@@TESTDATA_PLACEHOLDER@@", $testData)
	
	New-Item "$databaseFolder\\localdb.log" -type File | Out-Null
	New-Item "$databaseFolder\\localdb.properties" -type File | Out-Null
	New-Item "$databaseFolder\\localdb.script" -type File | Out-Null
	
	Set-Content "$databaseFolder\\localdb.properties" $HSQLDBproperties
	Set-Content "$databaseFolder\\localdb.script" $HSQLDBscript
}

function CreateDatabaseForPeer1()
{
	$location = "$configBaseDirectory\\peer1\\database"
	$name = "HSQLDB43066372BA"
	$schema = @"
CREATE MEMORY TABLE PUBLIC.PEERS(URL VARCHAR(256) NOT NULL PRIMARY KEY,FAILURE INTEGER NOT NULL)
"@
	$data = @"
INSERT INTO PEERS VALUES('http://localhost:9001',3)
"@
	
	CreateDatabase $location $name $schema $data
}

function CreateDatabaseForPeer2()
{
	$location = "$configBaseDirectory\\peer2\\database"
	$name = "HSQLDB43066372BB"
	$schema = @"
CREATE MEMORY TABLE PUBLIC.PEERS(URL VARCHAR(256) NOT NULL PRIMARY KEY,FAILURE INTEGER NOT NULL)
"@
	$data = @"
INSERT INTO PEERS VALUES('http://localhost:9000',3)
INSERT INTO PEERS VALUES('http://localhost:9002',3)
"@
	
	CreateDatabase $location $name $schema $data
}

function CreateDatabaseForPeer3()
{
	$location = "$configBaseDirectory\\peer3\\database"
	$name = "HSQLDB43066372BC"
	$schema = @"
CREATE MEMORY TABLE PUBLIC.PEERS(URL VARCHAR(256) NOT NULL PRIMARY KEY,FAILURE INTEGER NOT NULL)
"@
	$data = @"
INSERT INTO PEERS VALUES('http://localhost:9001',3)
"@
	
	CreateDatabase $location $name $schema $data
}

function CreateDatabaseForClient1()
{
	$location = "$configBaseDirectory\\client1\\database"
	$name = "HSQLDB43066372BD"
	$schema = @"
CREATE MEMORY TABLE PUBLIC.PEERS(URL VARCHAR(256) NOT NULL PRIMARY KEY,FAILURE INTEGER NOT NULL)
CREATE MEMORY TABLE PUBLIC.LOGGEDIN(USERNAME VARCHAR(128) NOT NULL PRIMARY KEY,PASSWORD VARCHAR(64) NOT NULL)
CREATE MEMORY TABLE PUBLIC.FINGERPRINTS(USERNAME VARCHAR(128) NOT NULL,TIMESTAMP TIMESTAMP NOT NULL,ARTIST VARCHAR(128) NOT NULL,SONGTITLE VARCHAR(128) NOT NULL,PRIMARY KEY(USERNAME,TIMESTAMP))
"@
	$data = @"
INSERT INTO PEERS VALUES('http://localhost:9000',5)
INSERT INTO FINGERPRINTS VALUES('John','2008-08-08 20:08:08.000000','Testartist','Testsongtitle')
"@
	
	CreateDatabase $location $name $schema $data
}

function CreateDatabaseForClient2()
{
	$location = "$configBaseDirectory\\client2\\database"
	$name = "HSQLDB43066372BE"
	$schema = @"
CREATE MEMORY TABLE PUBLIC.PEERS(URL VARCHAR(256) NOT NULL PRIMARY KEY,FAILURE INTEGER NOT NULL)
CREATE MEMORY TABLE PUBLIC.LOGGEDIN(USERNAME VARCHAR(128) NOT NULL PRIMARY KEY,PASSWORD VARCHAR(64) NOT NULL)
CREATE MEMORY TABLE PUBLIC.FINGERPRINTS(USERNAME VARCHAR(128) NOT NULL,TIMESTAMP TIMESTAMP NOT NULL,ARTIST VARCHAR(128) NOT NULL,SONGTITLE VARCHAR(128) NOT NULL,PRIMARY KEY(USERNAME,TIMESTAMP))
"@
	$data = @"
INSERT INTO PEERS VALUES('http://localhost:9001',5)
INSERT INTO PEERS VALUES('http://localhost:9002',5)
INSERT INTO FINGERPRINTS VALUES('Jane','2008-08-08 20:08:08.000000','Someone','Something')
"@
	
	CreateDatabase $location $name $schema $data
}

function CompileComponents()
{
	CompileServer
	CompilePeer
	CompileClient
	
	Write-Host "Compilation of components has finished." -ForegroundColor Green
}

function CreateDatabases()
{
	CreateDatabaseForClient1
	CreateDatabaseForClient2
	CreateDatabaseForPeer1
	CreateDatabaseForPeer2
	CreateDatabaseForPeer3
	Write-Host "Configuration databases have been created." -ForegroundColor Green
}

function RunComponents()
{
	StartServer @("9005")
	Write-Host "Press enter when server is up and running..." -ForegroundColor Yellow
	Read-Host
	StartPeer @("C:\\temp\\SWAzam\\peer1\\mp3s", "9000", "http://localhost:9005", "C:\\temp\\SWAzam\\peer1\\database\\localdb", "John", "Doe")
	StartPeer @("C:\\temp\\SWAzam\\peer2\\mp3s", "9001", "http://localhost:9005", "C:\\temp\\SWAzam\\peer2\\database\\localdb", "Jane", "Doe")
	StartPeer @("C:\\temp\\SWAzam\\peer3\\mp3s", "9002", "http://localhost:9005", "C:\\temp\\SWAzam\\peer3\\database\\localdb", "John", "Doe")
	Write-Host "Press enter when peers are up and running..." -ForegroundColor Yellow
	Read-Host
	StartClient @("C:\\temp\\SWAzam\\client1\\database\\localdb")
	StartClient @("C:\\temp\\SWAzam\\client2\\database\\localdb")
	Write-Host "All components for SWAzam demo are running now!" -ForegroundColor Green
}

##########################################################################################################################
# Execution is starting here
CompileComponents
CreateConfigDirectories
CreateDatabases
RunComponents

start 'http://localhost:8080'
Write-Host "Make sure to place MP3s in the peer libraries to allow successful track identification!" -ForegroundColor Yellow