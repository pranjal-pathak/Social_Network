import java.util.*;

public class SocialNetwork {

    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, User> users = new HashMap<>();
    private static Map<Integer, Post> posts = new HashMap<>();
    

    public static void main(String[] args) {
        System.out.println();
        System.out.println("<------- Welcome to News Feed ------>");
        System.out.println("--------------------------------------");
        System.out.println("|       $ Use These Commands $        |");
        System.out.println("|-------------------------------------|");
        System.out.println("| 1. signup username password         |");
        System.out.println("| 2. login username password          |");
        System.out.println("| 3. post text link                   |");
        System.out.println("| 4. follow FolloweeUsername          |");
        System.out.println("| 5. comment postId text              |");
        System.out.println("| 6. upvote postId                    |");
        System.out.println("| 7. downvote postId                  |");
        System.out.println("| 8. shownewsfeed                     |");
        System.out.println("| 9. shownewsfeed sortOption  |");
        System.out.println("| 10. signout                          |");
        System.out.println("| 11. <exit                           |");
        System.out.println("|-------------------------------------|");
        while (true) {
            
            System.out.print("Enter command: ");
            String command = scanner.nextLine();
            String[] parts = command.split("\\s+");
            String action = parts[0];

            switch (action) {
                case "signup":
                    signup(parts[1], parts[2]);
                    break;
                case "login":
                    login(parts[1], parts[2]);
                    break;
                case "post":
                    post(parts[1], parts[2]);
                    break;
                case "follow":
                    follow(parts[1], parts[2]);
                    break;
                case "reply":
                    reply(Integer.parseInt(parts[1]), parts[2]);
                    break;
                case "upvote":
                    upvote(Integer.parseInt(parts[1]));
                    break;
                case "downvote":
                    downvote(Integer.parseInt(parts[1]));
                    break;
                case "shownewsfeed":
                    showNewsFeed(parts.length > 1 ? parts[1] : null);
                    break;
                case "signout":
                    signout();
                    break;
                case "exit":
                    System.exit(0);
                default:
                    System.out.println("Invalid command.");
            }
        }
    }

    private static void signup(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already taken.");
        } else {
            users.put(username, new User(username, password));
            // ls.add(new );
            System.out.println("User account created successfully.");
        }
    }

    private static void signout() {
        Session.setUser(null);
    }

    private static void login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.authenticate(password)) {
            Session.setUser(user);
            System.out.println("Logged in as " + username);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void post(String text, String link) {
        Post post = new Post(Session.getUser(), text, link);
        posts.put(post.getId(), post);
        Session.getUser().addPost(post);
        System.out.println("Post created successfully.");
    }

    private static void follow(String username1, String username2) {
        User user1 = users.get(username1);
        User user2 = users.get(username2);
        if (user1 == null || user2 == null) {
            System.out.println("Invalid username.");
        } else if (user1 == user2) {
            System.out.println("You cannot follow yourself.");
        } else {
            user1.follow(user2);
            System.out.println("You are now following " + username2);
        }
    }

    private static void reply(int postId, String text) {
        Post post = posts.get(postId);
        if (post == null) {
            System.out.println("Invalid post ID.");
        } else {
            Comment comment = new Comment(Session.getUser(), text);
            post.addComment(comment);
            System.out.println("Comment added successfully.");
        }
    }

    private static void upvote(int postId) {
        Post post = posts.get(postId);
        if (post == null) {
            System.out.println("Invalid post ID.");
        } else {
            post.upvote();
            System.out.println("Post upvoted successfully.");
        }
    }

    private static void downvote(int postId) {
        Post post = posts.get(postId);
        if (post == null) {
            System.out.println("Invalid post ID.");
        } else {
            post.downvote();
            System.out.println("Post downvoted successfully.");
        }
    }

    private static void showNewsFeed(String sortOption) {
        List<Post> newsFeed = new ArrayList<>();
        User user = Session.getUser();
        if (user == null) {
            System.out.println("Please login first.");
            return;
        }

        for (User followedUser : user.getFollowedUsers()) {
            newsFeed.addAll(followedUser.getPosts());
        }

        if (sortOption == null || sortOption.equals("timestamp")) {
            Collections.sort(newsFeed, (p1, p2) -> p2.getTimestamp().compareTo(p1.getTimestamp()));
        } else if (sortOption.equals("score")) {
            Collections.sort(newsFeed, (p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
        } else if (sortOption.equals("comments")) {
            Collections.sort(newsFeed, (p1, p2) -> Integer.compare(p2.getCommentCount(), p1.getCommentCount()));
        } else {
            System.out.println("Invalid sort option.");
            return;
        }

        for (Post post : newsFeed) {
            System.out.println("Post ID: " + post.getId());
            System.out.println("Author: " + post.getAuthor().getUsername());
            System.out.println("Text: " + post.getText());
            System.out.println("Link: " + post.getLink());
            System.out.println("Score: " + post.getScore());
            System.out.println("Comments: " + post.getCommentCount());

            post.print_comment(post);

            System.out.println("Timestamp: " + getTimeAgo(post.getTimestamp()));
            System.out.println();
        }
       
    }
    

    private static String getTimeAgo(Date timestamp) {
        long diffInMillis = new Date().getTime() - timestamp.getTime();
        long diffInSeconds = diffInMillis / 1000;
        if (diffInSeconds < 60) {
            return diffInSeconds + "s ago";
        }
        long diffInMinutes = diffInSeconds / 60;
        if (diffInMinutes < 60) {
            return diffInMinutes + "m ago";
        }
        long diffInHours = diffInMinutes / 60;
        if (diffInHours < 24) {
            return diffInHours + "h ago";
        }
        long diffInDays = diffInHours / 24;
        if (diffInDays < 7) {
            return diffInDays + "d ago";
        }
        return timestamp.toString();
    }
}
