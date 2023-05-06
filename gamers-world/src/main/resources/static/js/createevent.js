function checkSelectedGame() {
    var selectedGames = document.querySelectorAll('input[name="selectedGame"]:checked');
    if (selectedGames.length > 1) {
        alert('You can only select 1 game!');
        return false;
    }
    else if (selectedGames.length == 0)
    {
        alert('Please choose at least 1 game.');
        return false;
    }
    return true;
}

function checkDate() {
    const date = new Date();
    let setDate = document.getElementById('date').value;

    const regex = /\d\d\/\d\d\/\d\d\d\d/;
    if(!regex.test(setDate)) {
        alert('Please enter a valid date.');
        return false;
    }

    let day  = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    let month2 = setDate.substring(1,2);
    let day2 = setDate.substring(3, 5);
    let year2 = setDate.substring(6, 10);

    const currDate = new Date(year, month, day, 0, 0, 0, 0);
    const eventDate = new Date(year2, month2, day2, 0, 0, 0, 0);
    
    if(eventDate.getTime() >= currDate.getTime()) {
        return true;
    }

    alert('Invalid Date: Entered date is in the past');
    return false;
}