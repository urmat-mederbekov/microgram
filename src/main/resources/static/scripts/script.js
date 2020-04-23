async function getPosts() {
    const response = await fetchAuthorised('http://localhost:8080/posts');
    if(response.ok){
        return await response.json();
    }else {
        alert("Error" + response.error());
    }
}
async function getComments() {
    const response = await fetchAuthorised('http://localhost:8080/comments');
    if(response.ok){
        return await response.json();
    }else {
        alert("Error" + response.error());
    }
}
async function getLikes(){
    const response = await  fetchAuthorised('http://localhost:8080/likes/liked');
    if(response.ok){
        return await response.json();
    }else {
        alert("Error" + response.error());
    }
}
async function loadPostsAndComments() {
    const posts = getPosts();
    const comments = getComments();
    const likes = getLikes();

    await posts.then(postData => {
        //iterate through posts
        postData.content.map((post) => {

            let postElement = createPostElement(post);
            let isLiked = false
            likes.then(likeData => {
                //iterate through likes
                likeData.content.map((liked) => {

                    let heart = postElement.querySelector(".heart");
                    let heartI = postElement.querySelector(".fa-heart");
                    // filter by like presence
                    if(liked.post.id === post.id){
                        like(heart, heartI);
                    }else {
                        unlike(heart, heartI);
                    }
                    isLiked = true;
                });
            });
            // if there is no any like don't mark liked
            if(isLiked === false){
                let heart = postElement.querySelector(".heart");
                let heartI = postElement.querySelector(".fa-heart");
                unlike(heart, heartI);
            }


            comments.then(commentData => {
                //iterate through comments
                commentData.content.map((comment) => {

                    // filter comments by relevance
                    if (comment.post.id === post.id) {

                        let commentElement = createCommentElement(comment);
                        // add a comment to comments
                        postElement.querySelector(".comments").appendChild(commentElement);
                    }
                });
            });
            document.querySelector('.posts-container').appendChild(postElement);
        });
        comment();
        // add button functions
        buttonAnimations();
    });
}

function showSplashScreen() {
    document.getElementById("page-splash").style.display = "flex";
    document.body.className = "no-scroll";
}
function hideSplashScreen() {
    document.getElementById("page-splash").style.display = 'none';
    document.body.className = "";
}
function createPostObject(image, description) {
    return new Post(image, description);
}
function createPostElement(post) {
    let postElement = document.createElement('div');
    postElement.className = "card my-3";
    postElement.innerHTML =
        `<!-- image block start -->` +
        `<div>` +
        `<button class="imageBut">` +
        `<img class="d-block w-100" src="/posts/image/${post.image}">`+
        `</button>` +
        `</div>` +
        `<!-- image block end -->` +
        `<div class="px-4 py-3">` +
        `<!-- post reactions block start -->` +
        `<!-- <div class="d-flex justify-content-around"> -->` +
        `<div class="justify-content-around">` +
        `<button class="button heartBut">` +
        `<span class="h1 mx-2 heart text-danger">` +
        `<i class="fas fa-heart"></i>` +
        `</span>` +
        `</button>` +
        `<button class="commentBut button">` +
        `<span class="h1 mx-2 muted">` +
        `<i class="far fa-comment"></i>` +
        `</span>` +
        `</button>` +
        `<span class="mx-auto"></span>` +
        `<button class="button bookmarkBut">` +
        `<span class="h1 mx-2 muted">` +
        `<i class="far fa-bookmark bookmark"></i>` +
        `</span>` +
        `</button>` +
        `<!-- post reactions block end -->` +
        `<hr>` +
        `<!-- post section start -->` +
        `<div class="posts">` +
        `<p>${post.description}</p>` +
        `</div>` +
        `<!-- post section end -->` +
        `<hr>` +
        `<!-- comments section start -->` +
        `<form class="commentForm" action="/comment" style="display: none;" method="post">` +
        `<div class="comments">` +
        `<input type="hidden" name="postId" value="${post.id}">`+
        `</div>` +
        `<textarea name="text"></textarea>` +
        `<button type="submit" class="send">send</button>` +
        `</form>` +
        `<!-- comments section end -->` +
        `</div>` +
        `</div>`;
    return postElement;
}
function addPostElement(postElement) {

    document.getElementsByClassName("col col-lg-7 posts-container")[0].append(postElement);
}
function post() {

    const postBut = document.getElementById("postBut");
    postBut.addEventListener("click", function () {

        const postForm = document.getElementById("postForm");
        let data = new FormData(postForm);
        fetch("/posts/post", {
            method: "POST",
            body: data
        });
        addPostElement(createPostElement(createPostObject(data.get("poster").name, data.get("description"))));
        buttonAnimations();
        comment();
        window.location.href = "/";
    });
}
post();
function createCommentObject(text, username, email) {
    return new Comment(text, username, email);
}
function createCommentElement(comment) {

    let commentElement = document.createElement("div");
    commentElement.className = "py-2 pl-3";
    commentElement.innerHTML =
        `<input type="hidden" name="userId" class="userId" value="${comment.commenter.email}">`+
        `<a href="#" class="muted">${comment.commenter.username}</a>` +
        `<p>${comment.text}</p>`;

    return commentElement;
}
function addCommentElement(commentElement, order) {
    document.getElementsByClassName("comments")[order].append(commentElement);
}

function buttonAnimations() {

    for (let i = 0; i < document.getElementsByClassName("card my-3").length; i++) {

        const commentForm = document.getElementsByClassName("commentForm")[i];
        const imageBut = document.getElementsByClassName("imageBut")[i];
        const commentBut = document.getElementsByClassName("commentBut")[i];
        let heartBut = document.getElementsByClassName("heartBut")[i];
        const bookmarkElement = document.getElementsByClassName("bookmark")[i];
        const bookmarkBut = document.getElementsByClassName("bookmarkBut")[i];
        let heart = document.getElementsByClassName("heart")[i];
        let heartI = document.getElementsByClassName("fa-heart")[i];

        // like image
        imageBut.addEventListener("dblclick", function() {

            if (heart.classList.contains("text-danger")) {
                unlike(heart, heartI);
            } else {
                like(heart, heartI);
            }
        });
        // like button
        heartBut.addEventListener("click", function(){
            if (heart.classList.contains("text-danger")) {
                unlike(heart, heartI);
            } else {
                like(heart, heartI);
            }
        });
        // bookmark button
        bookmarkBut.addEventListener("click", function(){
            if (bookmarkElement.classList.contains("far")) {
                bookmark(bookmarkElement);
            } else {
                unBookmark(bookmarkElement);
            }
        });
        // comment button
        commentBut.addEventListener("click", function(){
            if(commentForm.style.display === "none"){
                commentForm.style.display = "block";
            }else {
                commentForm.style.display = "none";
            }
        });
    }
}
function comment() {
    for (let i = 0; i < document.getElementsByClassName("commentForm").length; i++) {

        const commentForm = document.getElementsByClassName("commentForm")[i];
        commentForm.addEventListener("submit", function(event){
            event.preventDefault();
            const data = new FormData(commentForm);
            const postJSON = JSON.stringify(Object.fromEntries(data));
            let  user = restoreUser();
            console.log(user);
            fetchAuthorised('/comments/' + data.get("postId"), {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Basic ' + btoa(user.email + ':' + user.password)
                },
                body: postJSON,

                cache: 'no-cache',
                mode : 'cors'
            });
            let content = document.querySelectorAll('[name=text]')[i].value;
            if(content === undefined || "" === content.trim())
                return;
            addCommentElement(createCommentElement(createCommentObject(content, "you", "email")), i);
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

function bookmark(bookmark) {
    bookmark.classList.remove("far");
    bookmark.classList.add("fas");
}
function unBookmark(bookmark) {
    bookmark.classList.remove("fas");
    bookmark.classList.add("far");
}

/////////////////////////////////// Objects//////////////////////////////////////
class User{
    constructor(username, email, password){
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
class Post {
    constructor(image, description) {
        this.id = Math.random().toString(36).substring(2) + Date.now().toString(36);
        this.image = image;
        this.description = description;
    }
}
class Comment{
    constructor(text, username, email){
        this.text = text;
        this.commenter = new User(username, email)
    }
}

////////////////////////////////////registration//////////////////////////////////

const baseUrl = 'http://localhost:8080';

const registrationForm = document.getElementById('registration-form');
registrationForm.addEventListener('submit', onRegisterHandler);

function onRegisterHandler(e) {
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    createUser(data);
}

async function createUser(userFormData) {

    const userJSON = JSON.stringify(Object.fromEntries(userFormData));
    const settings = {
        method: 'POST',
        cache: 'no-cache',
        mode : 'cors',
        headers: {
            'Content-Type': 'application/json'
        },
        body: userJSON
    };
    const response = await fetch(baseUrl + '/users', settings);
    const responseData = await response.json();
    console.log(responseData);
}

/////////////////////////////////////login////////////////////////////////////////////

const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', onLoginHandler);


function onLoginHandler(e) {
    e.preventDefault();
    const form = e.target;
    const userFormData = new FormData(form);
    const user = Object.fromEntries(userFormData);
    saveUser(user);
    window.location.href = "/";
}
function saveUser(user) {
    const userAsJSON = JSON.stringify(user)
    localStorage.setItem('user', userAsJSON);
}
function restoreUser() {
    const userAsJSON = localStorage.getItem('user');
    const user = JSON.parse(userAsJSON);
    return user;
}
function updateOptions(options) {
    const update = { ...options };
    update.mode = 'cors';
    update.headers = { ... options.headers };
    update.headers['Content-Type'] = 'application/json';
    const user = restoreUser();
    if(user) {
        update.headers['Authorization'] = 'Basic ' + btoa(user.email + ':' + user.password);
    }
    return update;
}
function fetchAuthorised(url, options) {
    const settings = options || {};
    return fetch(url, updateOptions(settings));
}
function load() {
    const localUser = restoreUser();

    if(localUser.email !== null){
        hideSplashScreen();
        loadPostsAndComments();
    }else{
        showSplashScreen();
    }
}
load();