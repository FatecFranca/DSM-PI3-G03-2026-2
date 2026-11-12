console.log('prestador.js carregado!');


const usuario = JSON.parse(localStorage.getItem("usuario"));
const lista = document.getElementById("listaPedidos");


function criarCampo(texto, input) {
        const campo = document.createElement("div");
        campo.className = "campo";

        const label = document.createElement("label");
        label.textContent = texto;

        campo.appendChild(label);
        campo.appendChild(input);
        return campo;
}


function criarCardPedido(pedido) {
        const card = document.createElement("div");
        card.className = "passo";

        if (pedido.fotos && pedido.fotos.length > 0) {
                const img = document.createElement("img");
                img.src = pedido.fotos[0].url;
                img.alt = "Foto do problema";
                img.className = "foto-solicitacao";
                img.onerror = function () { img.remove(); };  
                card.appendChild(img);
        }

        const descricao = document.createElement("p");
        descricao.textContent = pedido.descricao;
        card.appendChild(descricao);

        const data = document.createElement("p");
        data.textContent = "Pedido em " + new Date(pedido.data).toLocaleDateString("pt-BR");
        card.appendChild(data);


        const form = document.createElement("form");
        form.className = "form-orcamento";

        const valor = document.createElement("input");
        valor.type = "number";
        valor.min = "1";
        valor.step = "0.01";
        valor.placeholder = "Ex.: 150";
        valor.required = true;

        const prazo = document.createElement("input");
        prazo.type = "number";
        prazo.min = "1";
        prazo.placeholder = "Ex.: 2";
        prazo.required = true;

        const recado = document.createElement("textarea");
        recado.rows = 2;
        recado.placeholder = "Ex.: Troco a resistência no mesmo dia.";

        const botao = document.createElement("button");
        botao.type = "submit";
        botao.textContent = "Enviar orçamento";

        const aviso = document.createElement("p");

        form.appendChild(criarCampo("Valor (R$)", valor));
        form.appendChild(criarCampo("Prazo (dias)", prazo));
        form.appendChild(criarCampo("Mensagem (opcional)", recado));
        form.appendChild(botao);
        form.appendChild(aviso);
        card.appendChild(form);

        form.addEventListener("submit", function (evento) {
                evento.preventDefault();

                const orcamento = {
                        solicitacaoId: pedido.id,
                        prestadorId: usuario.id,
                        valor: Number(valor.value),
                        prazoDias: Number(prazo.value),
                        mensagem: recado.value
                };

                fetch("http://localhost:8888/orcamentos", {
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify(orcamento)
                })
                        .then(function (resposta) {
                                if (resposta.ok) {
                                        aviso.textContent = "Orçamento enviado!";
                                        form.reset();
                                        botao.disabled = true;
                                        return;
                                }

                                return resposta.json().then(function (erro) {
                                        aviso.textContent = erro.mensagem || "Erro ao enviar. Confira os dados.";
                                });
                        })
                        .catch(function () {
                                aviso.textContent = "Não foi possível conectar ao servidor.";
                        });
        });

        return card;
}


function carregarPedidos() {
        if (!usuario || usuario.tipo !== "prestador") {
                lista.textContent = "Entre como prestador para ver os pedidos.";
                return;
        }

        Promise.all([
                fetch("http://localhost:8888/solicitacoes/status/ABERTA").then(function (r) { return r.json(); }),
                fetch("http://localhost:8888/solicitacoes/status/ORCAMENTO_ENVIADO").then(function (r) { return r.json(); })
        ])
                .then(function (resultados) {
                        const pedidos = resultados[0].concat(resultados[1]);
                        lista.innerHTML = "";

                        if (pedidos.length === 0) {
                                lista.textContent = "Nenhum pedido aguardando orçamento no momento.";
                                return;
                        }

                        pedidos.forEach(function (pedido) {
                                lista.appendChild(criarCardPedido(pedido));
                        });
                })
                .catch(function () {
                        lista.textContent = "Não foi possível carregar os pedidos.";
                });
}

carregarPedidos();