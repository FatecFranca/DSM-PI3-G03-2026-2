// Deixa só os números e coloca os pontos e o traço: 000.000.000-00
function mascaraCpf(valor) {
        valor = valor.replace(/\D/g, "").slice(0, 11);
        valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
        valor = valor.replace(/(\d{3})(\d)/, "$1.$2");
        valor = valor.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
        return valor;
}

// Formata o telefone: (16) 99999-9999 (celular) ou (16) 3333-4444 (fixo)
function mascaraTelefone(valor) {
        valor = valor.replace(/\D/g, "").slice(0, 11);

        if (valor.length > 10) {
                return valor.replace(/(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");
        }
        if (valor.length > 6) {
                return valor.replace(/(\d{2})(\d{4})(\d{0,4})/, "($1) $2-$3");
        }
        if (valor.length > 2) {
                return valor.replace(/(\d{2})(\d{0,5})/, "($1) $2");
        }
        return valor;
}

// Tira tudo que não é número (para mandar para a API)
function somenteNumeros(valor) {
        return valor.replace(/\D/g, "");
}

// Aplica as máscaras enquanto o usuário digita
document.getElementById("cpf").addEventListener("input", function (e) {
        e.target.value = mascaraCpf(e.target.value);
});

document.getElementById("telefone").addEventListener("input", function (e) {
        e.target.value = mascaraTelefone(e.target.value);
});