function checkSelectedGame() {
    var selectedGames = document.querySelectorAll('input[name="selectedGame"]:checked');
    if (selectedGames.length > 1) {
        alert('You can only select 1 game!');
        return false;
    }
    /*
    else if (selectedGames.length == 0)
    {
        alert('Please choose a game.');
        return false;
    }
    */
    return true;
}