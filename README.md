# Talkative   Messaging app



![logo](https://github.com/HamishHamiltonSmith/Talkative-Messaging-app/blob/main/info-images/logo.png)

A GUI based messaging app built with java (swing). It includes user profiles with bios and images along with standard
messaging app functionality like chat creation and deletion + messaging. Making this really taught me how much goes into
modern messaging apps, even a basic one like this took a good few months. 






## Download instructions ##


### Basic setup ###

If you want to try out the app for yourself, you will need to download run the build files for both client and server. This will most likely only work
if you are on linux. These will set up your directory structures.

### Making a user account ###

In order to make a new user account, add a directory to the Users folder on the server containing a file called user_chats.txt. Then on the client side,
go to the UserData folder and put the name of the user you created in the user.txt folder making sure there are no whitespaces (super secure right?).


## How it works ##

### Front-end ###

As mentioned earlier, javax.swing was used to create the GUI for the app. Here is a breakdown of what it looks like.
Here is a summary or where you can find their code:


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


