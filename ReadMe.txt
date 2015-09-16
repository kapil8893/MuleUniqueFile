Approach Taken:

1. Polling for the source directory at 5 minutes interval. Set autoDelete=false, to ensure that the file stays at the source location.
2. Using an Idempotent Filter which will derive uniqueness from FileName+FileSize+Timestamp.
3. The Idempotent filter will filter out the messages which matches the criteria, that is, for the files which are already processed.
4. Rest of the files will be written out to the target directory.


Approach Taken for Unit Testing:
1. Mule must be running
2. @Before executing the Test, the Test Case will write a file to the source location.
3. The @Test, will first wait for 5 mins (equavalent to the polling interval), and then will try to read the same file from the Target location.
4. And, it will compare the timestamp of the file written in the target directory with the system timestamp (upto minute unit).
5. Both these timestamps should differ, which would mean that the file was written to the target directory in the past.

Notes:
1. Using File protocol instead of SFTP becuase of lack of SFTP server. Tried installing some tools on my windows machine like OpenSSH (Cygwin) and FreeSshd, but ran into issues with them.


Execution Steps:
mvn clean deploy -Dmule.home=C:\MyData\Softwares\mule-standalone-3.7.0\mule-standalone-3.7.0


Reading Logs:
Referring to the app log line#103, we can see that the poller found the same file again, however, the filter didn't let it pass through to next step in the flow.