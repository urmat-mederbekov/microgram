<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <title>${user.username!""}</title>
    <link href="/css/freemarker.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="table flex flex-col">
        <div class="header flex">
            <div class="column">
                Name
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
        <div class="row flex">
            <div class="column">
                ${user.username!""}
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
        <#list user.posts as post>
            <div class="row ${post?item_parity}  flex">
                <div class="column">${post.image!""}</div>
                <div class="column">${post.description!""}</div>
                <div class="column">${post.dateTime!""}</div>
                <div class="column">${post.numOfLikes!""}</div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>