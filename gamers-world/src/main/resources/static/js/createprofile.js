function checkSelectedGames() {
    var selectedGames = document.querySelectorAll('input[name="selectedGames"]:checked');
    if (selectedGames.length > 5) {
        alert('You can only select up to 5 games!');
        return false;
    }
    else if (selectedGames.length == 0)
    {
        alert('Please choose at least 1 game.');
        return false;
    }
    return true;
}

function checkEmail() {
    const email = document.getElementById("email").value;
    const invalid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
    if (!invalid.test(email)) {
      alert('Please enter a valid email address.');
      return false;
    }
  
    return true;
  }

  function checkPassword() {
    const password = document.getElementById("password").value;
  
    if (password.length < 8 || password.length > 16) {
      alert('Your password should be between 8-16 characters long.');
      return false;
    }
  
    return true;
  }
  
  