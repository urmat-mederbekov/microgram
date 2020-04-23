const registrationForm = document.getElementById('registration-form');
registrationForm.addEventListener('submit', onRegisterHandler);

function onRegisterHandler(e) {
    e.preventDefault();
    const form = e.target;
    const data = new FormData(form);
    createUser(data);
}

async function createUser(userFormData) {

    const baseUrl = 'http://localhost:8080';
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

const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', onLoginHandler);


function onLoginHandler(e) {
    e.preventDefault();
    const form = e.target;
    const userFormData = new FormData(form);
    const user = Object.fromEntries(userFormData);
    saveUser(user);
    fetchAuthorised('http://localhost:8080/posts')
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
        update.headers['Authorization'] = 'Basic ' + btoa(user.username + ':' + user.password);
    }
    return update;
}
function fetchAuthorised(url, options) {
    const settings = options || {}
    return fetch(url, updateOptions(settings));
}