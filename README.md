# Talkative   Messaging app



![logo](https://github.com/HamishHamiltonSmith/Talkative-Messaging-app/blob/main/info-images/logo.png)

A GUI based messaging app built with java (swing) for linux distrubutions. It includes user profiles with bios and images along with standard
messaging app functionality like chat creation and deletion + messaging. Making this really taught me how much goes into
modern messaging apps, even a basic one like this took a good few months. 






## Download instructions ##


### Basic setup ###

If you want to try out the app for yourself, you will need to first clone this repository. Before doing anything be sure to extract the zip file and **make sure the resulting folder is called Talkative**. You then want to navigate to the build files (Talkative/build) and run them.

```sudo python3 client.py```
```sudo python3 server.py```


One sets up the server and one the client though you can run them both to use Talkative on your machine. This will most likely only work with root permissions.
You will be asked the path to the directory that the Talkative folder is in for both builds. Now this is done, you simply need to run the java files. The path to the two main files are:

Server - /usr/share/Talkative/server/src/Main.java

Client - /usr/share/Talkative/client/src/Main.java

I would recomend opening these up in a code editor like vs code to run them. **You must run both files with root permissions** and remember that to use the client you must first start the server. **The client will attempt to connect to a talkative server on your machine. You will have to edit the IP in the client code if you want to connect to a diferent machine.**
 

### Notes ###

This version of talkative (V1.1) is **not secure!** There is no encryption, no user security, server security or request security. **The server has no idea
if a request is dangerous, if the request is edited it could change or delete, any file on the server if ran with permissions!** In future versions this may
be changed but for now be weary. There are also currently no character limits on user input so be carefull!


## How it works ##

Here is the cool stuff if you are interested

### Front-end ###

As mentioned earlier, javax.swing was used to create the GUI for the app.
Here is a summary or where you can find client page code:


- Home screen (gui_main.java): displays chats and other options
- Chat screen (gui_main.java): displays chat messages and allows them to be written
- Profile screen (Profile.java): displays a summary of the user profile
- Bio edit (Bio_edit.java): a simple text editor
- Picture edit (create_new.java): a file system for selecting a user picture
- Create new Screen (create_new.java): contains search boxes and text boxes for chat creation
- Error messages (Error.java): display software errors



### Back-end ###

The backend of the app uses vanilla java socket capabilities. You can find all backend protocols in the socket_utils file. Through the use of 5 basic protocols, client sides can create and delete chats, message and fetch data. Every user has their own directory on the server, this contains the chats they are involved in (user_chats.txt). A seperate directory contains all chat files (txt).

**Messaging**

When the client is started, it uses a PULL request to fetch all chats a user is in, these will then be displayed. Once clicked on another pull request is 
used to fetch chat messages. To post a message all the server needs is a POST request with a chat path and message, it can then append this message to
the file, encryption could also easily be added to this.

**Chat and profile management**

When a user creates a chat, they specify the name and users to add. To add users they must search for them. The way this is done is horifically ineficent at
the moment... A FETCH request to the server returns a list of all users (see what I mean), a linear search is then conducted, the results of which are
given as options to the user. Once the create button is pressed, a CREATE request is sent, from this a new chat file will be added, a header will be written to it containing the names of all users in it. The users who have been added to the chat will have their user_chats file updated to contain the new chat path.

To delete a chat, the client will send a DELETE request to the server, it will first take the header of the chat and use it to access the user_chat files of all
users within the chat, it will remove the chats path from them, the chat file will then be permenantly deleted.

For bio and profile-pic updates no networking was needed due to them being stored locally.


