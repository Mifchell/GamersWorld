    const MAX_GAMES = 5; // maximum number of games
    const checkboxes = document.querySelectorAll('.game-checkbox'); // select all game checkboxes
    const errorMessage = document.getElementById('error-message'); // select the error message element

    // function to handle checkbox changes
    function handleCheckboxChange() {
        const numChecked = document.querySelectorAll('.game-checkbox:checked').length; // count number of checked checkboxes

        // disable remaining checkboxes if limit is reached
        checkboxes.forEach(function(checkbox) {
            if (numChecked >= MAX_GAMES && !checkbox.checked) {
                checkbox.disabled = true;
            } else {
                checkbox.disabled = false;
            }
        });

        // show error message if limit is reached
        if (numChecked > MAX_GAMES) {
            errorMessage.style.display = 'block';
        } else {
            errorMessage.style.display = 'none';
        }
    }

    // add event listener to each checkbox
    checkboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', handleCheckboxChange);
    });