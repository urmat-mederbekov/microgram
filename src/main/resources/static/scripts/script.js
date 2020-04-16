async function getPosts() {
    const response = await fetch('http://localhost:8080/rest/posts');
    if(response.ok){
        return await response.json();
    }else {
        alert("Error" + response.error());
    }
}
async function getComments() {
    const response = await fetch('http://localhost:8080/comments');
    if(response.ok){
        return await response.json();
    }else {
        alert("Error" + response.error());
    }
}
function loadPostsAndComments() {
    const posts =  getPosts();
    const comments = getComments();

    posts.then(postData => {
        postData.content.map((post) => {
            //create card
            let cardElement = document.createElement('div');
            cardElement.className = "card my-3";
            cardElement.innerHTML =
                '<!-- image block start -->' +
                '<div>' +
                '<button class="imageBut">' +
                `<img class="d-block w-100" src="/rest/posts/image/${post.image}">`+
                '</button>' +
                '</div>' +
                '<!-- image block end -->' +
                '<div class="px-4 py-3">' +
                '<!-- post reactions block start -->' +
                '<!-- <div class="d-flex justify-content-around"> -->' +
                '<div class="justify-content-around">' +
                '<button class="button heartBut">' +
                '<span class="h1 mx-2 heart text-danger">' +
                '<i class="fas fa-heart"></i>' +
                '</span>' +
                '</button>' +
                '<button class="commentBut button">' +
                '<span class="h1 mx-2 muted">' +
                '<i class="far fa-comment"></i>' +
                '</span>' +
                '</button>' +
                '<span class="mx-auto"></span>' +
                '<button class="button bookmarkBut">' +
                '<span class="h1 mx-2 muted">' +
                '<i class="far fa-bookmark bookmark"></i>' +
                '</span>' +
                '</button>' +
                '<!-- post reactions block end -->' +
                '<hr>' +
                '<!-- post section start -->' +
                '<div class="posts">' +
                '<p>' + post.description + '</p>' +
                '</div>' +
                '<!-- post section end -->' +
                '<hr>' +
                '<!-- comments section start -->' +
                '<form class="commentForm" action="/comment" style="display: none;" method="post">' +
                '<div class="comments">' +
                '<input type="hidden" name="postId" value="' + post.id + '">'+
                '</div>' +
                '<textarea name="text"></textarea>' +
                '<button type="submit" class="send">send</button>' +
                '</form>' +
                '<!-- comments section end -->' +
                '</div>' +
                '</div>';

            comments.then(commentData => {
                commentData.content.map((comment) => {
                    // filter comments by relevance
                    if(comment.post.id === post.id) {
                        //create comment
                        let commentElement = document.createElement('div');
                        commentElement.className = "py-2 pl-3";
                        commentElement.innerHTML =
                            '<div class="py-2 pl-3">' +
                            '<input type="hidden" name="userId" class="userId" value="' + comment.commenter.email+'">'+
                            '<a href="#" class="muted">' + comment.commenter.username + '</a>' +
                            '<p>' + comment.text + '</p>'+
                            '</div>';
                        // add a comment to comments
                        cardElement.querySelector(".comments").appendChild(commentElement);
                    }
                });
            });
            //add card to container
            document.querySelector('.posts-container').appendChild(cardElement);
        });
        // add button functions
        bookmarkButton();
        likeButton();
        imageButton();
        commentButton();
        CommenterySecBut();
    });

}
loadPostsAndComments();


function hideSplashScreen() {
    let noScroll = document.querySelector(".page-splash");
    noScroll.classList.remove("page-splash");
}
function showSplashScreen() {
    let noScroll = document.querySelector("#page-splash");
    noScroll.classList.add("page-splash");
}
class User{
    constructor(name, email, password, isAuthrorized){
        this.name = name;
        this.email = email;
        this.password = password;
        this.isAuthrorized = isAuthrorized;
    }
}
class Post {
    constructor(image, description) {
        this.image = image;
        this.description = description;
    }
}
class Comment{
    constructor(id, text, dataTime, post){
        this.text = text;
        this.dataTime = dataTime;
        this.post = post;
    }
}
function createPostObject2(image, description) {
    return new Post(image, description);
}
function createPostElement2(post) {
    let postElement = document.createElement('div');
    postElement.className = "card my-3";
    postElement.innerHTML =
        '<!-- image block start -->' +
        '<div>' +
        '<button class="imageBut">' +
        `<img class="d-block w-100" src="/rest/posts/image/${post.image}">`+
        '</button>' +
        '</div>' +
        '<!-- image block end -->' +
        '<div class="px-4 py-3">' +
        '<!-- post reactions block start -->' +
        '<!-- <div class="d-flex justify-content-around"> -->' +
        '<div class="justify-content-around">' +
        '<button class="button heartBut">' +
        '<span class="h1 mx-2 heart text-danger">' +
        '<i class="fas fa-heart"></i>' +
        '</span>' +
        '</button>' +
        '<button class="commentBut button">' +
        '<span class="h1 mx-2 muted">' +
        '<i class="far fa-comment"></i>' +
        '</span>' +
        '</button>' +
        '<span class="mx-auto"></span>' +
        '<button class="button bookmarkBut">' +
        '<span class="h1 mx-2 muted">' +
        '<i class="far fa-bookmark bookmark"></i>' +
        '</span>' +
        '</button>' +
        '<!-- post reactions block end -->' +
        '<hr>' +
        '<!-- post section start -->' +
        '<div class="posts">' +
        '<p>' + post.description + '</p>' +
        '</div>' +
        '<!-- post section end -->' +
        '<hr>' +
        '<!-- comments section start -->' +
        '<form class="commentForm" action="/comment" style="display: none;" method="post">' +
        '<div class="comments">' +
        '<input type="hidden" name="postId" value="' + post.id + '">'+
        '</div>' +
        '<textarea name="text"></textarea>' +
        '<button type="submit" class="send">send</button>' +
        '</form>' +
        '<!-- comments section end -->' +
        '</div>' +
        '</div>';
    return postElement;
}
function addPostElement(postElement) {

    document.getElementsByClassName("col col-lg-7 posts-container")[0].append(postElement);
}
function createCommentObject(order) {
    let userLink = document.createElement('a');
    userLink.setAttribute("href", "#");
    userLink.setAttribute("class", "muted");
    userLink.innerHTML = "you";
    let pTag = document.createElement('p');
    let content = document.querySelectorAll('[name=text]')[order].value;
    if (content === undefined || content.trim().length === 0)
        return;
    pTag.innerHTML = content;
    let comBlock = document.createElement('div');
    comBlock.append(userLink);
    comBlock.append(pTag);
    return comBlock;
}
function createCommentElement(comment) {
    if (comment === undefined)
        return;
    comment.className = "py-2 pl-3";
    return comment;
}
function addCommentElement(commentElement, order) {
    if (commentElement === undefined)
        return;
    document.getElementsByClassName("comments")[order].append(commentElement);
}
function commentButton() {
    for (let i = 0; i < document.getElementsByClassName("commentForm").length; i++) {
        const commentForm = document.getElementsByClassName("commentForm")[i];
        commentForm.addEventListener("submit", function(event){
            event.preventDefault();

            const data = new FormData(commentForm);


            fetch('/comments/comment', {
                method: 'POST',
                body: data
            });
                // .then(r => r.json())
                // .then(data => {window.location.href = "/posts"});
            addCommentElement(createCommentElement(createCommentObject(i)), i);
        });

    }
}
function upload() {
    const postBut = document.getElementById("postBut");
    postBut.addEventListener("click", function () {

        const postForm = document.getElementById("postForm");
        let data = new FormData(postForm);
        fetch("/rest/posts/post", {
            method: "POST",
            body: data
        });

        addPostElement(createPostElement2(createPostObject2(data.get("poster").name), data.get("description")));
    });
}
upload();
function likeButton() {
    for (let i = 0; i < document.getElementsByClassName("heart").length; i++) {
        let heartBut = document.getElementsByClassName("heartBut")[i];
        let heart = document.getElementsByClassName("heart")[i];
        let heartI = document.getElementsByClassName("fa-heart")[i];
        heartBut.addEventListener("click", function(){
            if (heart.classList.contains("text-danger")) {
                unlike(heart, heartI);
            } else {
                like(heart, heartI);
            }
        });
    }
}
function unlike(heart, heartI) {
    heart.classList.remove("text-danger");
    heart.classList.add("muted");
    heartI.classList.remove("fas");
    heartI.classList.add("far");
}
function like(heart, heartI) {
    heart.classList.add("text-danger");
    heart.classList.remove("muted");
    heartI.classList.remove("far");
    heartI.classList.add("fas");
}
function imageButton() {
    for (let i = 0; i < document.getElementsByClassName("imageBut").length; i++) {
        const imageBut = document.getElementsByClassName("imageBut")[i];
        imageBut.addEventListener("dblclick", function(event) {
            let heart = document.getElementsByClassName("heart")[i];
            let heartI = document.getElementsByClassName("fa-heart")[i];
            if (heart.classList.contains("text-danger")) {
                unlike(heart, heartI);
            } else {
                like(heart, heartI);
            }
        });
    }
}
function bookmarkButton(){
    for (let i = 0; i < document.getElementsByClassName("bookmark").length; i++) {
        const bookmarkElement = document.getElementsByClassName("bookmark")[i];
        const bookmarkBut = document.getElementsByClassName("bookmarkBut")[i];
        bookmarkBut.addEventListener("click", function(){
            if (bookmarkElement.classList.contains("far")) {
                bookmark(bookmarkElement);
            } else {
                unbookmark(bookmarkElement);
            }
        });
    }
}
function CommenterySecBut() {
    for (let i = 0; i < document.getElementsByClassName("commentBut").length; i++) {
        const commentBut = document.getElementsByClassName("commentBut")[i];
        const commentery = document.getElementsByClassName("commentForm")[i];
        commentBut.addEventListener("click", function(){
            if(commentery.style.display === "none"){
                commentery.style.display = "block";
            }else {
                commentery.style.display = "none";
            }
            console.log(commentery.style.display);
        });
    }
}
function bookmark(bookmark) {
    bookmark.classList.remove("far");
    bookmark.classList.add("fas");
}
function unbookmark(bookmark) {
    bookmark.classList.remove("fas");
    bookmark.classList.add("far");
}