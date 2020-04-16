<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lesson 57. Javascript 101</title>
    <link rel="stylesheet"   href="/css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body class="no-scroll">
<div class="buttons">
    <button class="show" onclick="showSplashScreen()">sign out</button>
    <form id="postForm" action="/rest/posts/post" method="post" enctype="multipart/form-data">
        <input type="file" name="poster">
<#--        <input type="hidden" name="id">-->
        <textarea name="description"></textarea>
        <!--            <input type="submit">-->
        <button type="button" id="postBut">Post</button>
    </form>
</div>
<div class="container">
    <div class="row justify-content-center">
        <div class="col col-lg-7 posts-container">
            <!-- single post start -->
            <!-- single post end -->
        </div>
    </div>
</div>
<!-- Splash screen -->
<section id="page-splash" class="page-splash">
    <div class="card" id="registration-form">
        <div class="card-body">
            <h3 class="h3">Зарегистрируйтесь</h3>
        </div>
        <div class="buttons">
            <button class="hide" onclick="hideSplashScreen()">sign in</button>
        </div>
    </div>
</section>
<script src="/scripts/script.js"></script>
</body>
</html>