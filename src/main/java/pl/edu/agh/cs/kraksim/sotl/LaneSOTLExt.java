package pl.edu.agh.cs.kraksim.sotl;

import org.apache.log4j.Logger;
import pl.edu.agh.cs.kraksim.core.Lane;
import pl.edu.agh.cs.kraksim.iface.block.BlockIView;
import pl.edu.agh.cs.kraksim.iface.block.LaneBlockIface;
import pl.edu.agh.cs.kraksim.iface.eval.LaneEvalIface;
import pl.edu.agh.cs.kraksim.iface.mon.CarDriveHandler;
import pl.edu.agh.cs.kraksim.iface.mon.LaneMonIface;
import pl.edu.agh.cs.kraksim.iface.mon.MonIView;

import java.util.HashSet;
import java.util.Set;

class LaneSOTLExt implements LaneEvalIface {
	private static final Logger LOGGER = Logger.getLogger(LaneSOTLExt.class);

	private final SOTLParams params;
	private final LaneBlockIface laneBlockExt;

	private volatile int carCount = 0;
	private int sotlLaneValue = 0;
	private final String id;
	private final Set<Object> drivers;

	LaneSOTLExt(final Lane lane, MonIView monView, BlockIView blockView, SOTLParams params) {
		LOGGER.trace(lane);

		this.params = params;
		id = lane.getOwner().getId() + ':' + lane.getAbsoluteNumber();
		drivers = new HashSet<>();

		laneBlockExt = blockView.ext(lane);
		LaneMonIface laneMonitoring = monView.ext(lane);
		int zoneBegin = lane.getOwner().getLength() - Math.min(params.zoneLength, lane.getLength());
		laneMonitoring.installInductionLoop(zoneBegin, new CarDriveHandler() {
			public synchronized void handleCarDrive(int velocity, Object driver) {
				LOGGER.trace(" >>>>>>> INDUCTION LOOP FIRED" + lane + "  " + carCount + "++");
				carCount++;
				drivers.add(driver);
			}
		});

		int zoneEnd = lane.getOwner().getLength();
		laneMonitoring.installInductionLoop(zoneEnd, new CarDriveHandler() {
			public synchronized void handleCarDrive(int velocity, Object driver) {
				if (!drivers.contains(driver)) {
					LOGGER.warn("Leaving car should have been registered at the enterence. Driver id = " + driver.hashCode());
					//return;
				}
				LOGGER.trace(" >>>>>>> INDUCTION LOOP FIRED" + lane + "  " + carCount + "--");
				carCount--;
			}
		});
	}

	void turnEnded() {
		if (laneBlockExt.isBlocked()) {
			sotlLaneValue += carCount;
		} else {
			sotlLaneValue = 0;
		}
	}

	public float getEvaluation() {
		LOGGER.trace(id + " carCount=" + carCount + ", sotlValue=" + sotlLaneValue + ", blocked=" + laneBlockExt.isBlocked());
		if (sotlLaneValue > params.threshold) {
			return sotlLaneValue;
		} else {
			return 0;
		}
	}

	public int getMinGreenDuration() {
		int ret = (int) ((carCount) * (float) params.carStartDelay + (carCount / (float) params.carMaxVelocity));
		return Math.max(ret, SOTLParams.minimumGreen);
	}
}
