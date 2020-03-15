<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <title>Users</title>
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
                Email
            </div>
        </div>
        <#list users as user>
            <div class="row ${user?item_parity} flex">
                <div class="column">
                    <a href="/users/user?userId=${user.id}" rel="link" methods="post">${user.accountName}</a>
                </div>

            </div>

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
        </#list>
    </div>
</div>
</body>
</html>