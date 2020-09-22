package av1;

import java.util.*;

public class MaquinaDeTuring {
	private String fita, estadoAtual, estadoReijeitado, estadoAceito, estadoInicial;
	private int index;

	private Set<String> espacos;
	private Set<Transicao> transicoes;

	class Transicao {
		String lerEstado, escreverEstado;
		char lerSimbolo, escreverSimbolo;
		boolean moverParaDireita;

		boolean temConflito(String state, char symbol) {
			return state.equals(lerEstado) && symbol == lerSimbolo;
		}
	}

	public MaquinaDeTuring() {
		espacos = new HashSet<String>();
		transicoes = new HashSet<Transicao>();
		estadoInicial = new String("");
		estadoAceito = new String("");
		estadoReijeitado = new String("");
		fita = new String("");
		estadoAtual = new String("");
		index = 0;

	}
	
	public void addFita(String novaFita) {
		fita = novaFita;
	}

	public boolean Iniciar(boolean logar) {
		estadoAtual = estadoInicial;

		while (!estadoAtual.equals(estadoAceito) && !estadoAtual.equals(estadoReijeitado)) {
			boolean temTransicao = false;
			Transicao transicaoAtual = null;

			if (logar) {
				if (index > 0) {
					System.out.println(
							fita.substring(0, index) + " " + estadoAtual + " " + fita.substring(index));
				} else {
					System.out.println(" " + estadoAtual + " " + fita.substring(index));
				} 
			}
			Iterator<Transicao> transicaoIte = transicoes.iterator();
			while (transicaoIte.hasNext() && temTransicao == false) {
				Transicao proxTransicao = transicaoIte.next();
				if (proxTransicao.lerEstado.equals(estadoAtual)	&& proxTransicao.lerSimbolo == fita.charAt(index)) {
					temTransicao = true;
					transicaoAtual = proxTransicao;
				}
			}

			if (!temTransicao) {
				System.err.println("Não existe uma transição para o estado '" + estadoAtual + "' quando o símbolo for '"+ fita.charAt(index)+"'");
				return false;
			} else {
				estadoAtual = transicaoAtual.escreverEstado;				
				char[] fitaTemp = fita.toCharArray();
				char[] fitaTempMaior = new char[fita.length()+1];
				fitaTemp[index] = transicaoAtual.escreverSimbolo;
				fita = new String(fitaTemp);
				if (transicaoAtual.moverParaDireita) {
					index++;
				} else {
					index--;
				}

				if (index < 0) {
					index = 0;
					fitaTempMaior[0]= '_';
					for(int i=1; i<=fita.length(); i++) {
						fitaTempMaior[i] = fitaTemp[i-1]; 
					}
					fita = new String(fitaTempMaior);
				}
				

				while (fita.length() <= index) {
					fita = fita.concat("_");
				}

			}

		}
		System.out.println("Estado final da fita: "+fita);
		return estadoAtual.equals(estadoAceito);
	}

	public boolean addEstado(String novoEstado) {
		if (espacos.contains(novoEstado)) {
			return false;
		} else {
			espacos.add(novoEstado);
			return true;
		}
	}

	public boolean setEstadoInicial(String novoEstadoInicial) {
		if (espacos.contains(novoEstadoInicial)) {
			estadoInicial = novoEstadoInicial;
			return true;
		} else {
			return false;
		}
	}

	public boolean setEstadoAceito(String novoEstadoAceito) {
		if (espacos.contains(novoEstadoAceito) && !estadoReijeitado.equals(novoEstadoAceito)) {
			estadoAceito = novoEstadoAceito;
			return true;
		} else {
			return false;
		}

	}

	public boolean setEstadoReijeitado(String novoEstadoRejeitado) {
		if (espacos.contains(novoEstadoRejeitado) && !estadoAceito.equals(novoEstadoRejeitado)) {
			estadoReijeitado = novoEstadoRejeitado;
			return true;
		} else {
			return false;
		}
	}

	public boolean addTransicao(String estadoLidoTemp, char simboloLidoTemp, String escreverEstadoTemp, char escreverSimboloTemp, boolean mover) {
		if (!espacos.contains(estadoLidoTemp) || !espacos.contains(escreverEstadoTemp)) {
			throw new NullPointerException("Estado nao cadastrado");			
		}

		boolean conflito = false;
		Iterator<Transicao> transicaoIte = transicoes.iterator();
		while (transicaoIte.hasNext() && conflito == false) {
			Transicao nextTransition = transicaoIte.next();
			if (nextTransition.temConflito(estadoLidoTemp, simboloLidoTemp)) {
				conflito = true;
			}

		}
		if (conflito) {
			return false;
		} else {
			Transicao novaTransicao = new Transicao();
			novaTransicao.lerEstado = estadoLidoTemp;
			novaTransicao.lerSimbolo = simboloLidoTemp;
			novaTransicao.escreverEstado = escreverEstadoTemp;
			novaTransicao.escreverSimbolo = escreverSimboloTemp;
			novaTransicao.moverParaDireita = mover;
			transicoes.add(novaTransicao);
			return true;
		}
	}
}