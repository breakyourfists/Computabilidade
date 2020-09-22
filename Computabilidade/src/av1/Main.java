package av1;

public class Main {
	public static void main(String[] args) {
		MaquinaDeTuring mt = new MaquinaDeTuring();
		
		mt.addFita("1011");
		mt.addEstado("qi");
		mt.addEstado("q1");
		mt.addEstado("qf");
		mt.setEstadoAceito("qf");
		mt.setEstadoInicial("qi");		
		mt.addTransicao("qi", '1', "qi", '1', true);
		mt.addTransicao("qi", '0', "qi", '0', true);
		mt.addTransicao("qi", '_', "q1", 'Y', false);
		mt.addTransicao("q1", '1', "q1", '1', false);
		mt.addTransicao("q1", '0', "q1", '0', false);
		mt.addTransicao("q1", '_', "qf", 'X', true);
		boolean fim = mt.Iniciar(false);
		System.out.println(fim);
	}
}

