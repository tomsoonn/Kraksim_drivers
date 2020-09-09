package pl.edu.agh.cs.kraksim.main;

import pl.edu.agh.cs.kraksim.core.City;
import pl.edu.agh.cs.kraksim.main.gui.SimulationVisualizer;
import pl.edu.agh.cs.kraksim.ministat.CityMiniStatExt;
import pl.edu.agh.cs.kraksim.ministat.MiniStatEView;

import java.io.PrintWriter;

class ConsoleSimulationVisualizer implements SimulationVisualizer {
	private final CityMiniStatExt stat;
	private final PrintWriter writer = new PrintWriter(System.out);

	public ConsoleSimulationVisualizer(final City city, final MiniStatEView statView) {
		stat = statView.ext(city);
		//    System.out.println( "\n\n     Starting Krasim Simulation     " );
		//    System.out.println( "------------------------------------" );
		//    System.out.println( "Akademia Gorniczo Hutnicza 2005-2007\n\n" );
	}

	public void startLearningPhase(final int phaseNum) {
		writer.printf("LEARNING PHASE: %d\n", phaseNum + 1);
	}

	public void startTestingPhase() {
		writer.printf("TESTING PHASE\n");
	}

	public void endPhase() {
		writer.printf("\n");
	}

	public void end(long elapsed) {
		writer.printf("Simulation time:" + (elapsed / 1000.0) + "\nTHE END\n");
		//writer.close();
	}

	public void update(final int turn) {
//        if ( turn % 100 == 0 ) {
		//writer.printf("\rturn: %6d %6d %6d %6.2f %6.2f", turn, stat.getTravelCount(), stat.getCarCount(), stat.getAvgVelocity(), stat.getTravelLength() / 1000);
		//    }
	}
}
