console.log('orcamentos.js carregado!');

const usuario = JSON.parse(localStorage.getItem("usuario"));
const lista = document.getElementById("listaOrcamentos");
const API = "http://localhost:8888";

const textoStatus = {
        PENDENTE: "Aguardando sua resposta",
        ACEITO: "Aceito - serviço em andamento",
        RECUSADO: "Recusado",
        CANCELADO: "Cancelado",
        CONCLUIDO: "Concluído"
};


function buscar(caminho) {
        return fetch(API + caminho).then(function (r) { return r.json(); });
}


function atualizar(caminho, corpo) {
        fetch(API + caminho, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: corpo ? JSON.stringify(corpo) : null
        })
                .then(function (resposta) {
                        if (!resposta.ok) {
                                return resposta.json().then(function (erro) {
                                        alert(erro.mensagem || "Não foi possível atualizar.");
                                });
                        }
                        carregarOrcamentos();
                });
}

function criarBotao(texto, classe, aoClicar) {
        const botao = document.createElement("button");
        botao.textContent = texto;
        botao.className = classe;
        botao.addEventListener("click", function () {
                botao.disabled = true;   
                aoClicar();
        });
        return botao;
}

function criarCard(orcamento) {
        const card = document.createElement("div");
        card.className = "passo";

        const icone = document.createElement("i");
        icone.className = "fa-solid fa-user-gear";
        card.appendChild(icone);

        const valor = document.createElement("h3");
        valor.textContent = orcamento.valor.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });
        card.appendChild(valor);

        const prestador = document.createElement("p");
        prestador.innerHTML = "<strong>Profissional:</strong> ";
        prestador.append(orcamento.nomePrestador);
        card.appendChild(prestador);

        const pedido = document.createElement("p");
        pedido.innerHTML = "<strong>Pedido:</strong> ";
        pedido.append(orcamento.descricaoPedido.slice(0, 60) + "...");
        card.appendChild(pedido);

        const prazo = document.createElement("p");
        prazo.innerHTML = "<strong>Prazo:</strong> ";
        prazo.append(orcamento.prazoDias + (orcamento.prazoDias === 1 ? " dia" : " dias"));
        card.appendChild(prazo);

        if (orcamento.mensagem) {
                const mensagem = document.createElement("p");
                mensagem.textContent = '"' + orcamento.mensagem + '"';
                card.appendChild(mensagem);
        }

        const status = document.createElement("p");
        status.innerHTML = "<strong>Status:</strong> ";
        status.append(textoStatus[orcamento.status] || orcamento.status);
        card.appendChild(status);

        
        if (orcamento.status === "PENDENTE") {
                card.appendChild(criarBotao("Aceitar", "btn", function () {
                        atualizar("/orcamentos/" + orcamento.id + "/status", { status: "ACEITO" });
                }));
                card.appendChild(criarBotao("Recusar", "btn btn-claro", function () {
                        atualizar("/orcamentos/" + orcamento.id + "/status", { status: "RECUSADO" });
                }));
        } else if (orcamento.status === "ACEITO") {
                card.appendChild(criarBotao("Marcar como concluído", "btn", function () {
                        atualizar("/orcamentos/" + orcamento.id + "/concluir");
                }));
        } else if (orcamento.status === "CONCLUIDO") {
                const avaliar = document.createElement("a");
                avaliar.href = "avaliacao.html?orcamento=" + orcamento.id;
                avaliar.className = "btn";
                avaliar.textContent = "Avaliar";
                card.appendChild(avaliar);
        }

        return card;
}

function carregarOrcamentos() {
        if (!usuario || usuario.tipo !== "cliente") {
                lista.textContent = "Entre como cliente para ver seus orçamentos.";
                return;
        }

        
        buscar("/solicitacoes/cliente/" + usuario.id)
                .then(function (pedidos) {
                        
                        return Promise.all(pedidos.map(function (pedido) {
                                return buscar("/orcamentos/solicitacao/" + pedido.id).then(function (orcamentos) {
                                        return orcamentos.map(function (o) {
                                                o.descricaoPedido = pedido.descricao;
                                                return o;
                                        });
                                });
                        }));
                })
                .then(function (listas) {
                        const orcamentos = [].concat.apply([], listas);

                        
                        return Promise.all(orcamentos.map(function (o) {
                                return buscar("/prestadores/" + o.prestadorId)
                                        .then(function (p) { o.nomePrestador = p.nome; return o; })
                                        .catch(function () { o.nomePrestador = "Profissional"; return o; });
                        }));
                })
                .then(function (orcamentos) {
                        lista.innerHTML = "";

                        if (orcamentos.length === 0) {
                                lista.textContent = "Você ainda não recebeu nenhum orçamento.";
                                return;
                        }

                        orcamentos.forEach(function (o) {
                                lista.appendChild(criarCard(o));
                        });
                })
                .catch(function () {
                        lista.textContent = "Não foi possível carregar os orçamentos.";
                });
}

carregarOrcamentos();
