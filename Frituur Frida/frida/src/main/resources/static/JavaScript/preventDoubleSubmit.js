// Disabelen van de submit knopt toevoegen in toevoegen.html
// tot dat de post request is voltooid

// Je zoekt het <form> element. Je koppelt een functie aan het onsubmit event.
// De browser voert die functie uit als de gebruiker de form submit.
document.querySelector("form").onsubmit = function() {
    // De expressie this geeft de gesubmitte form.
    // Je zoekt in die form de submit knop. Je disabelt die knop.
    this.querySelector("button").disabled = true;
};