<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
          name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">
    <title>Users</title>
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

        </div>
        <#list users as user>
            <div class="row ${user?item_parity} flex">
                <div class="column">
                    <a href="/users/user?userId=${user.id}" rel="link" methods="post">${user.accountName}</a>
                </div>
                <div class="column">
                    ${user.email}
                </div>
            </div>
        </#list>
    </div>
</div>
</body>
</html>