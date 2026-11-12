
const linkSair = document.getElementById("sair");

if (linkSair) {
        linkSair.addEventListener("click", function (evento) {
                evento.preventDefault();
                localStorage.removeItem("usuario");
                window.location.href = "index.html";
        });
}
// Se ninguém estiver logado, manda para o login
if (!localStorage.getItem("usuario")) {
        window.location.href = "login.html";
}