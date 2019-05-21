(function () {
    const emailInput = document.getElementById('email');
    const driveInBtn = document.getElementById('btn-drive-in');
    driveInBtn.addEventListener('click', () => {
        fetch('google/signin' , {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({email: emailInput.value}),
        }).then(response => response.json())
        .then(user => {
            window.location = user.authorizationUrl;
        });
    });
})();
