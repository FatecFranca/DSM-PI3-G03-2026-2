console.log('avaliacao.js carregado!');

const usuario = JSON.parse(localStorage.getItem("usuario"));
const form = document.getElementById("formAvaliacao");
const mensagem = document.getElementById("mensagem");


const orcamentoId = new URLSearchParams(window.location.search).get("orcamento");

let orcamento = null;


if (!orcamentoId) {
        mensagem.textContent = "Escolha um serviço concluído na página de orçamentos para avaliar.";
} else {
        fetch("http://localhost:8888/orcamentos/" + orcamentoId)
                .then(function (resposta) { return resposta.json(); })
                .then(function (dados) { orcamento = dados; })
                .catch(function () {
                        mensagem.textContent = "Não foi possível carregar o serviço.";
                });
}

form.addEventListener("submit", function (evento) {
        evento.preventDefault();

        if (!usuario || !orcamento) {
                mensagem.textContent = "Não foi possível identificar o serviço ou o cliente.";
                return;
        }

        const estrelaMarcada = document.querySelector('input[name="nota"]:checked');

        const avaliacao = {
                orcamentoId: orcamento.id,
                prestadorId: orcamento.prestadorId,
                clienteId: usuario.id,
                nota: Number(estrelaMarcada.value),
                comentario: document.getElementById("comentario").value
        };

        fetch("http://localhost:8888/avaliacoes", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(avaliacao)
        })
                .then(function (resposta) {
                        if (resposta.ok) {
                                mensagem.textContent = "Obrigado! Sua avaliação foi enviada.";
                                form.reset();
                                return;
                        }
                        return resposta.json().then(function (erro) {
                                mensagem.textContent = erro.mensagem || "Erro ao enviar a avaliação.";
                        });
                })
                .catch(function () {
                        mensagem.textContent = "Não foi possível conectar ao servidor.";
                });
});