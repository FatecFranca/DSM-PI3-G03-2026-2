console.log('solicitacoes.js carregado!');


const usuario = JSON.parse(localStorage.getItem("usuario"));

const form = document.getElementById("formSolicitacao");
const mensagem = document.getElementById("mensagem");


function lerFoto(arquivo) {
        return new Promise(function (resolve) {
                const leitor = new FileReader();
                leitor.onload = function () {
                        resolve({ url: leitor.result, legenda: arquivo.name });
                };
                leitor.readAsDataURL(arquivo);
        });
}

form.addEventListener("submit", function (evento) {
        evento.preventDefault();

        if (!usuario) {
                mensagem.textContent = "Faça login para pedir um orçamento.";
                return;
        }

        const arquivos = document.getElementById("fotos").files;
        mensagem.textContent = "Enviando...";


        Promise.all(Array.from(arquivos).map(lerFoto))
                .then(function (fotos) {
                        const solicitacao = {
                                clienteId: usuario.id,
                                descricao: document.getElementById("descricao").value,
                                fotos: fotos
                        };

                        return fetch("http://localhost:8888/solicitacoes", {
                                method: "POST",
                                headers: { "Content-Type": "application/json" },
                                body: JSON.stringify(solicitacao)
                        });
                })
                .then(function (resposta) {
                        if (resposta.ok) {
                                mensagem.textContent = "Pedido enviado! Aguarde os orçamentos.";
                                form.reset();
                                carregarSolicitacoes();
                        } else {
                                mensagem.textContent = "Erro ao enviar o pedido. Confira os dados.";
                        }
                })
                .catch(function () {
                        mensagem.textContent = "Não foi possível conectar ao servidor.";
                });
});

const lista = document.getElementById("listaSolicitacoes");


const textoStatus = {
        ABERTA: "Aguardando orçamentos",
        ORCAMENTO_ENVIADO: "Orçamentos recebidos",
        EM_ANDAMENTO: "Serviço em andamento",
        CONCLUIDA: "Concluída",
        CANCELADA: "Cancelada"
};


function criarCard(solicitacao) {
        const card = document.createElement("div");
        card.className = "passo";

        if (solicitacao.fotos && solicitacao.fotos.length > 0) {
                const img = document.createElement("img");
                img.src = solicitacao.fotos[0].url;
                img.alt = "Foto do problema";
                img.className = "foto-solicitacao";
                card.appendChild(img);
        }

        const descricao = document.createElement("p");
        descricao.textContent = solicitacao.descricao;
        card.appendChild(descricao);

        const status = document.createElement("p");
        status.innerHTML = "<strong>Status:</strong> ";
        status.append(textoStatus[solicitacao.status] || solicitacao.status);
        card.appendChild(status);

        const data = document.createElement("p");
        data.textContent = "Pedido em " + new Date(solicitacao.data).toLocaleDateString("pt-BR");
        card.appendChild(data);

        return card;
}


function carregarSolicitacoes() {
        if (!usuario) {
                lista.textContent = "Faça login para ver suas solicitações.";
                return;
        }

        fetch("http://localhost:8888/solicitacoes/cliente/" + usuario.id)
                .then(function (resposta) {
                        return resposta.json();
                })
                .then(function (solicitacoes) {
                        lista.innerHTML = "";

                        if (solicitacoes.length === 0) {
                                lista.textContent = "Você ainda não fez nenhum pedido.";
                                return;
                        }

                        solicitacoes.forEach(function (solicitacao) {
                                lista.appendChild(criarCard(solicitacao));
                        });
                })
                .catch(function () {
                        lista.textContent = "Não foi possível carregar as solicitações.";
                });
}

carregarSolicitacoes();