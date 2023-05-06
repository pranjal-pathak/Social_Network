# Social_Network
This is a Java-based social network application that allows users to signup, login, create posts, follow other users, reply to posts, upvote/downvote posts, and view a newsfeed. The application is run on the command line, and all data is stored in memory.
Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

Prerequisites
To run this application, you will need:

Java Development Kit (JDK) version 8 or later installed on your machine.
An Integrated Development Environment (IDE) like Eclipse, NetBeans, or IntelliJ IDEA.
Installing
Follow these steps to get the application running:

->Clone the repository to your local machine using git clone https://github.com/YOUR-USERNAME/social-network-app.git.
->Open the project in your IDE.
->Build and run the SocialNetwork.java file.
Usage
To use the application, follow these steps:

Run the SocialNetwork.java file.
Enter commands in the command line interface as follows:
signup <username> <password>: Create a new user account with the specified username and password.
login <username> <password>: Login to the application with the specified username and password.
post <text> <link>: Create a new post with the specified text and link.
follow <username1> <username2>: Follow user2 with user1's account.
reply <post_id> <text>: Add a comment to a post with the specified post ID and text.
upvote <post_id>: Upvote a post with the specified post ID.
downvote <post_id>: Downvote a post with the specified post ID.
shownewsfeed <sort_option>: Display the news feed with posts sorted by the specified option (timestamp, score, or comments).
signout: Sign out of the application.
exit: Exit the application.
