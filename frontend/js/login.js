
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

        fetch("http://localhost:8888/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email: email, senha: senha })
        })
                .then(function (resposta) {
                        return resposta.json().then(function (dados) {
                                if (!resposta.ok) {
                                        mensagem.textContent = dados.mensagem || "E-mail ou senha incorretos.";
                                        return;
                                }

                                // guarda quem entrou: {id, nome, tipo}
                                localStorage.setItem("usuario", JSON.stringify(dados));

                                if (dados.tipo === "prestador") {
                                        window.location.href = "painel-prestador.html";
                                } else {
                                        window.location.href = "painel.html";
                                }
                        });
                })
                .catch(function () {
                        mensagem.textContent = "Não foi possível conectar ao servidor.";
                });
});
