
const form = document.getElementById("formLogin");
const mensagem = document.getElementById("mensagem");

form.addEventListener("submit", function (e) {
        e.preventDefault();

        const email = document.getElementById("email").value.trim();
        const senha = document.getElementById("senha").value;

        if (senha.length < 6) {
                mensagem.textContent = "A senha deve ter pelo menos 6 caracteres.";
                return;
        }

        mensagem.textContent = "";



        console.log("Login enviado:", email);
});
