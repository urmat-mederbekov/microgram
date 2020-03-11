<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <title>accountName</title>
    <link href="css/freemarker.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="table flex flex-col">
        <div class="header flex">
            <div class="column">
                Name
            </div>
            <div class="column">
                Email
            </div>
            <div class="column">
                Posts
            </div>
            <div class="column">
                Followers
            </div>
            <div class="column">
                Following
            </div>
        </div>
        <div class="column">
            ${user.accountName!""}
        </div>
        <div class="column">
            ${user.numOfPosts!""}
        </div>
        <div class="column">
            ${user.numOfFollowers!""}
        </div>
        <div class="column">
            ${user.numOfFollowings!""}
        </div>
    </div>
    <div class="table flex flex-col">
        <div class="header flex">
            <div class="column">
                Image
            </div>
            <div class="column">
                Description
            </div>
            <div class="column">
                Date
            </div>
            <div class="column">
                Likes
            </div>
        </div>
        <div class="column">
            <#list user.posts as post>
                <div>${post.image!""}</div>
                <div>${post.description!""}</div>
                <div>${post.dateTime!""}</div>
                <div>${post.numOfLikes!""}</div>
            </#list>
        </div>
    </div>
</div>
</body>
</html>