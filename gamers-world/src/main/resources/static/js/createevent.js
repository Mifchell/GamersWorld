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
    // Don't know if we want each event to be forced to assign a game
    return true;
}