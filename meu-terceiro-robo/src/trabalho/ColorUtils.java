package trabalho;

public class ColorUtils {	
	
	public static int getColor(int red, int green, int blue) {
		if (isAzulEscuro(red, green, blue)) {
			return EnumColor.AZUL_ESCURO.getValor();
		}

		if (isVerde(red, green, blue)) {
			return EnumColor.VERDE.getValor();
		}

		if (isVermelho(red, green, blue)) {
			return EnumColor.VERMELHO.getValor();
		}

		if (isRosa(red, green, blue)) {
			return EnumColor.ROSA.getValor();
		}
		
		if (isAzulClaro(red, green, blue)) {
			return EnumColor.AZUL_CLARO.getValor();
		}
		
		return 0;
	}
	
	private static boolean isRosa(int red, int green, int blue) {
		if (red  > 210 && red <= 280) {
			if (green >= 150 && green < 190) {
				if (blue >= 140 && blue <= 175) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isAzulEscuro(int red, int green, int blue) {
		if (red >= 30 && red <= 70) {
			if (green >= 30 && green <= 80) {
				if (blue >= 30 && blue <= 70) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isVerde(int red, int green, int blue) {
		if (red >= 55 && red <= 100) {
			if (green >= 110 && green <= 155) {
				if (blue >= 50 && blue <= 85) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isAzulClaro(int red, int green, int blue) {
		if (red >= 180 && red < 245) {
			if (green >= 210 && red < 250) {
				if (blue >= 210 && blue < 250) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static boolean isVermelho(int red, int green, int blue) {
		if (red >= 190) {
			if (green <= 80) {
				if (blue <= 80) {
					return true;
				}
			}
		}
		return false;
	}
	
	private enum EnumColor {
		ROSA(1),
		VERDE(2),
		AZUL_ESCURO(3),
		AZUL_CLARO(4),
		VERMELHO(5);
		
		private int valor;
		
		private EnumColor(int valor) {
			this.valor = valor;
		}

		public int getValor() {
			return valor;
		}
	}
}

/*Color color = cs.getColor();
System.out.println(color.getRed() + "," + color.getGreen() + "," + color.getBlue());
int cor = ColorUtils.getColor(color.getRed(), color.getGreen(), color.getBlue());

switch(cor) {
case 1:
	System.out.println("Rosa");
	break;
	
case 2:
	System.out.println("Verde");
	break;

case 3:
	System.out.println("Azul escuro");
	break;

case 4:
	System.out.println("Azul claro");
	break;
	
case 5:
	System.out.println("Amarelo");
	break;
default:
	System.out.println("N‹o encontrei");
}*/