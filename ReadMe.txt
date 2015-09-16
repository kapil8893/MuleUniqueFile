Approach Taken:
1. Polling for the source directory at 5 minutes interval. Set autoDelete=false, to ensure that the file stays at the source location.
2. Using an Idempotent Filter which will derive uniqueness from FileName+FileSize+Timestamp.
3. The Idempotent filter will filter out the messages which matches the criteria, that is, for the files which are already processed. One can use In-Memory or Persistent file store for the Idempotenet filter based on the requirement.
4. Rest of the files will be written out to the target directory with Timestamp appended to the name.


Approach Taken for Unit Testing:
1. Mule must be running
2. @Before executing the Test, the Test Case will write a file to the source location.
3. The @Test, will first wait for 5 mins (equivalent to the polling interval), and then will try to read the files from the Target location.
4. Here the file count having name starting from "TestFile" (with which the file was written in step 2) should be 1.

Notes:
1. Using File protocol instead of SFTP becuase of lack of SFTP server. Tried installing some tools on my windows machine like OpenSSH (Cygwin) and FreeSshd, but ran into issues with them. I am assuming that it should not be an issue.


Execution Steps:
mvn clean deploy -Dmule.home=C:\MyData\Softwares\mule-standalone-3.7.0\mule-standalone-3.7.0


Reading Logs:
Referring to the app log line#103, we can see that the poller found the same file again, however, the filter didn't let it pass through to next step in the flow.