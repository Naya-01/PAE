// source regex phone number : https://ihateregex.io/expr/phone/

import Member from "../../Domain/Member";
import Address from "../../Domain/Address";
import Notification from "../Module/Notification";
import MemberLibrary from "../../Domain/MemberLibrary";

const memberLibrary = new MemberLibrary();
const regOnlyNumbersAndDash = new RegExp('^[0-9-]+$');
const regNumberPhone =
    new RegExp('^[+]?[(]?[0-9]{3}[)]?[- .]?[0-9]{3}[- .]?[0-9]{4,6}$');
//starting with numbers
const regOnlyLettersAndNumbers = new RegExp('^[0-9]+[a-zA-Z]?$');
//starting with numbers
const regOnlyLettersAndNumbersOrNothing =
    new RegExp('^([0-9]+[a-zA-Z]?)*$');
const regOnlyLetters = new RegExp('^[a-zA-Z éàùöèêûî\']+$');
const regOnlyLettersAndDash = new RegExp('^[a-zA-Z éàùöèê\'ûî-]+$');
const toast = new Notification().getNotification("top-end");

const htmlPage = `
          <div class="container mt-5">
        <div class="border border-5 border-dark p-5">
            <div class="fs-1 text-center">Inscription</div>
          <div class="mx-5">
            <form>
              <div class="form-group mt-3">
                <label>Pseudonyme</label>
                <input id="username" class="form-control" 
                  placeholder="pseudonyme" type="text">
              </div>
              
              <div class="row mt-3">
                <div class="form-group col">
                    <label>Nom</label>
                    <input id="lastname" class="form-control" placeholder="nom" 
                      type="text">
                </div>
                <div class="form-group col">
                    <label>Prénom</label>
                    <input id="firstname" class="form-control" 
                      placeholder="prénom" type="text">
                  </div>
              </div>
              <div class="row mt-3">
                <div class="form-group col">
                  <label>Rue</label>
                  <input id="street" class="form-control" placeholder="rue" 
                    type="text">
                </div>
                <div class="form-group col">
                <label>Numéro de téléphone</label>
                <input id="phone_number" class="form-control" 
                  placeholder="numéro de téléphone" type="text">
              </div>
              </div>
              
              <div class="row mt-3">
                <div class="col form-group">
                    <label>Numéro</label>
                    <input id="building_number" class="form-control" 
                      placeholder="numéro" type="text">
                </div>
                <div class="col form-group">
                    <label>Boîte</label>
                    <input id="unit_number" class="form-control" 
                      placeholder="boîte" type="text">
                </div>
                <div class="col form-group">
                    <label>Code postal</label>
                    <input id="postcode" class="form-control" placeholder="CP" 
                      type="text">
                </div>
              </div>
              <div class="row mt-3">
                <div class="col form-group mt-3">
                    <label>Commune</label>
                    <input id="commune" class="form-control" 
                      placeholder="commune" type="text">
                  </div>
                  <div class="col form-group mt-3">
                    <label>Pays</label>
                    <input id="country" class="form-control" placeholder="pays"
                      type="text">
                  </div>
              </div>
              
              <div class="form-group mt-3">
                <label>Mot de passe</label>
                <input id="password" class="form-control" 
                  placeholder="mot de passe" type="password">
              </div>
              <div class="text-center">
                <button class="btn btn-lg btn-primary mt-3" id="submitRegister" 
                  type="submit">S'inscrire</button>
              <div class="text-center">
            </form>
          </div>
        </div>
      </div>
      `

const RegisterPage = async () => {
  const pageDiv = document.querySelector("#page");
  pageDiv.innerHTML = htmlPage;

  let btnSubmit = document.getElementById("submitRegister");

  btnSubmit.addEventListener("click", async e => {
    e.preventDefault();
    //member fields
    let username = document.getElementById("username");
    let lastname = document.getElementById("lastname");
    let firstname = document.getElementById("firstname");
    let phoneNumber = document.getElementById("phone_number");
    let password = document.getElementById("password");

    //address fields
    let street = document.getElementById("street");
    let buildingNumber = document.getElementById("building_number");
    let unitNumber = document.getElementById("unit_number");
    let postcode = document.getElementById("postcode");
    let commune = document.getElementById("commune");
    let country = document.getElementById("country");

    const notNullFields = [username, lastname, firstname, password,
      buildingNumber, street, postcode, commune, country];

    notNullFields.forEach(function (item) {
      if (item.classList.contains("border-danger")) {
        item.classList.remove("border-danger");
      }
    });

    if (phoneNumber.classList.contains("border-danger")) {
      phoneNumber.classList.remove("border-danger");
    }
    if (unitNumber.classList.contains("border-danger")) {
      unitNumber.classList.remove("border-danger");
    }

    let allNotNullFieldsFilled = true;

    //check if all not null fields are filled
    notNullFields.forEach(function (item) {
      if (item.value.trim().length === 0) {
        item.classList.add("border-danger");
        if (allNotNullFieldsFilled) {
          allNotNullFieldsFilled = false;
        }
      }
    });

    if (!allNotNullFieldsFilled) {
      toast.fire({
        icon: 'error',
        title: 'Veuillez remplir tout les champs obligatoires !'
      })
    } else if (username.value.trim().length > 50) {
      username.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le pseudonyme est trop grand'
      })
    } else if (lastname.value.trim().length > 50 ||
        !regOnlyLetters.test(lastname.value.trim())) {
      lastname.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le nom est trop grand ou est invalide'
      })
    } else if (firstname.value.trim().length > 50 ||
        !regOnlyLetters.test(firstname.value.trim())) {
      firstname.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le prénom est trop grand ou est invalide'
      })
    } else if (phoneNumber.value.trim().length !== 0
        && !regNumberPhone.test(phoneNumber.value.trim())) {
      phoneNumber.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le numéro de téléphone est invalide'
      })
    } else if (
        unitNumber.value.trim().length > 15
        || (unitNumber.value.trim().length !== 0
            && !regOnlyLettersAndNumbersOrNothing.test(unitNumber.value.trim())
        )) {
      unitNumber.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le numéro de boite est trop grand ou est invalide'
      })
    } else if (buildingNumber.value.trim().length > 8 ||
        !regOnlyLettersAndNumbers.test(buildingNumber.value.trim())) {
      buildingNumber.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le numéro de maison est trop grand ou est invalide'
      })
    } else if (street.value.trim().length > 50 ||
        !regOnlyLettersAndDash.test(street.value.trim())) {
      street.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le nom de rue est trop grand ou est invalide'
      })
    } else if (postcode.value.trim().length > 15 ||
        !regOnlyNumbersAndDash.test(postcode.value.trim())) {
      postcode.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le numéro de code postal est trop grand ou est invalide'
      })
    } else if (commune.value.trim().length > 50 ||
        !regOnlyLettersAndDash.test(commune.value.trim())) {
      commune.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le nom de commune est trop grand ou est invalide'
      })
    } else if (country.value.trim().length > 50 ||
        !regOnlyLettersAndDash.test(country.value.trim())) {
      country.classList.add("border-danger");
      toast.fire({
        icon: 'error',
        title: 'Le nom de pays est trop grand ou est invalide'
      })
    } else {
      let address = new Address(unitNumber.value.trim(),
          buildingNumber.value.trim(), street.value.trim(),
          postcode.value.trim(), commune.value.trim(), country.value.trim());
      let member = new Member(username.value.trim(), lastname.value.trim(),
          firstname.value.trim(), password.value.trim(),
          phoneNumber.value.trim(), address);
      // Requête DB inscription et redirect
      await memberLibrary.registerMember(member);
      toast.fire({
        icon: 'success',
        title: `Vous êtes désormais dans l'attente de la validation d'un 
          administrateur de votre profil`
      });
    }
  });
};

export default RegisterPage;
