package model.geneticAlgorithm;

import model.chromosome.TSPChromosome;
import model.geneticAlgorithm.crossover.CrossoverInterface;
import model.geneticAlgorithm.fitnessFunction.FitnessFunctionInterface;
import model.geneticAlgorithm.mutation.MutationInterface;
import model.geneticAlgorithm.selection.SelectionInterface;

public class TSPGeneticAlgorithm extends AbstractGeneticAlgorithm<TSPChromosome> {

	public TSPGeneticAlgorithm(FitnessFunctionInterface func, SelectionInterface selectionStrategy, CrossoverInterface crossoverStrategy,
			MutationInterface mutationStrategy, int populationNum, boolean useElitism, double elitePercentage, int maxGenerationNum,
			double crossProb, double mutationProb) {
		super(func, selectionStrategy, crossoverStrategy, mutationStrategy, populationNum, useElitism, elitePercentage,
				maxGenerationNum, crossProb, mutationProb);
	}

	@Override
	public void initialize() {
		this.population.clear();
		
		for (int i = 0; i < this.populationNum; i++) {
			TSPChromosome chr = new TSPChromosome(Cities.number, this.fitnessFunc);
			chr.initialize();
			chr.setAptitude(chr.evaluate());
			this.population.add(chr);
		}
	}
	
	public String toString() {
		if(this.population == null || this.population.size() == 0) return "";
		String s = new String();
		
		s += "pos     genotype     aptitude phenotype" + System.lineSeparator();
		for (int i = 0; i < this.population.size(); i++) {
			TSPChromosome elem = this.population.get(i);
			s += i + " " + elem + " " + String.format("%5f", elem.getAptitude()) + " ";
			
			for (Double el : elem.getPhenotype()) {
				s += String.format("%.5f", el) + " ";
			}
			s = s.substring(0, s.length() - 1);
			
			s += System.lineSeparator();
		}
		
		return s;
	}
	
	public static class Cities {
		public static int number = 27;
		private static int[][] DIST = {
			{0},
			{171},
			{369, 294},
			{366, 537, 663},
			{525, 696, 604, 318},
			{540, 515, 809, 717, 1022},
			{646, 817, 958, 401, 694, 620},
			{488, 659, 800, 243, 536, 583, 158},
			{504, 675, 651, 229, 89, 918, 605, 447},
			{617, 688, 484, 618, 342, 1284, 1058, 900, 369},
			{256, 231, 525, 532, 805, 284, 607, 524, 701, 873},
			{207, 378, 407, 256, 318, 811, 585, 427, 324, 464, 463},
			{354, 525, 332, 457, 272, 908, 795, 637, 319, 263, 610, 201},
			{860, 1031, 1172, 538, 772, 1118, 644, 535, 683, 1072, 1026, 799, 995},
			{142, 313, 511, 282, 555, 562, 562, 404, 451, 708, 305, 244, 445, 776},
			{640, 615, 909, 817, 1122, 100, 720, 683, 1018, 1384, 384, 911, 1008, 1218, 662},
			{363, 353, 166, 534, 438, 868, 829, 671, 485, 335, 584, 278, 166, 1043, 479, 968},
			{309, 480, 621, 173, 459, 563, 396, 238, 355, 721, 396, 248, 458, 667, 486, 663, 492},
			{506, 703, 516, 552, 251, 1140, 939, 781, 323, 219, 856, 433, 232, 1006, 677, 1240, 350, 690},
			{495, 570, 830, 490, 798, 274, 322, 359, 694, 1060, 355, 587, 797, 905, 406, 374, 831, 339, 1029},
			{264, 415, 228, 435, 376, 804, 730, 572, 423, 367, 520, 179, 104, 944, 380, 904, 99, 393, 336, 732},
			{584, 855, 896, 255, 496, 784, 359, 201, 407, 796, 725, 511, 733, 334, 500, 884, 761, 391, 730, 560, 668},
			{515, 490, 802, 558, 866, 156, 464, 427, 762, 1128, 259, 655, 865, 973, 472, 256, 861, 407, 1097, 118, 779, 628},
			{578, 653, 899, 358, 676, 468, 152, 115, 595, 999, 455, 526, 736, 650, 464, 568, 770, 278, 968, 244, 671, 316, 312},
			{762, 933, 1074, 440, 674, 1020, 546, 437, 585, 974, 928, 696, 897, 98, 678, 1120, 945, 569, 908, 807, 846, 236, 875, 352},
			{251, 422, 563, 115, 401, 621, 395, 237, 297, 663, 417, 190, 400, 609, 167, 721, 434, 58, 632, 397, 335, 333, 465, 336, 511},
			{473, 482, 219, 644, 436, 997, 939, 781, 506, 265, 713, 388, 187, 1153, 615, 1097, 129, 602, 313, 941, 209, 877, 1009, 880, 1055, 544},
			{150, 75, 219, 516, 675, 590, 796, 638, 654, 613, 306, 357, 444, 1010, 292, 690, 278, 459, 628, 611, 340, 734, 583, 694, 912, 401, 407}
		};
		
		public static int distance(cityNames from, cityNames to) {
			return distance(from.ordinal(), to.ordinal());
		}
		
		public static int distance(int from, int to) {
			if(from == to)
				return 0;
			else if(to > from)
				return DIST[from + (to - from)][to - (to - from)];
			else
				return DIST[from][to];
		}
	}
	
	public enum cityNames {
		Albacete, Alicante, Almeria, Avila, Badajoz, Barcelona, Bilbao, Burgos, Caceres, Cadiz, Castellon, Ciudad_Real, Cordoba, A_Coruna,
		Cuenca, Gerona, Granada, Guadalajara, Huelva, Huesca, Jaen, Leon, Lerida, Logrono, Lugo, Madrid, Malaga, Murcia
	}

}
