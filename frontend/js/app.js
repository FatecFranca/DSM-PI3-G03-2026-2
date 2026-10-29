
const saudacao = document.getElementById('saudacao');
const hora = new Date().getHours();

let mensagem;

if (hora < 12) {
    mensagem = 'Bom dia';
} else if (hora < 18) {
    mensagem = 'Boa tarde';
} else {
    mensagem = 'Boa noite';
}

saudacao.textContent = `${mensagem}! Seja bem-vindo à plataforma Apoio`;