function checkSelectedGames() {
    var selectedGames = document.querySelectorAll('input[name="selectedGames"]:checked');
    if (selectedGames.length > 5) {
        alert('You can only select up to 5 games!');
        return false;
    }
    return true;
}