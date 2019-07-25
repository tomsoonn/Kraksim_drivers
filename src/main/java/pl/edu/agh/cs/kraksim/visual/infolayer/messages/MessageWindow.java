package pl.edu.agh.cs.kraksim.visual.infolayer.messages;import java.awt.*;import javax.swing.*;import pl.edu.agh.cs.kraksim.core.Gateway;import pl.edu.agh.cs.kraksim.core.Intersection;import pl.edu.agh.cs.kraksim.core.Link;import pl.edu.agh.cs.kraksim.visual.infolayer.IntersectionPanel;import pl.edu.agh.cs.kraksim.ministat.GatewayMiniStatExt;import pl.edu.agh.cs.kraksim.ministat.LinkMiniStatExt;import pl.edu.agh.cs.kraksim.ministat.RouteStat;import pl.edu.agh.cs.kraksim.real_extended.RealSimulationParams;public class MessageWindow extends JFrame {	private static final long serialVersionUID = 1005619753174732046L;    private IntersectionPanel intersectionPanel;//    public MessageWindow(String title, String message, Point p){		initComponents(title, message, p, null);	}		public MessageWindow(Link link, LinkMiniStatExt miniStat, Point p){		String title = "Link info nr: " + link.getLinkNumber();		initComponents(title, constructInfoMessage(link, miniStat), p, null);	}		public MessageWindow(Intersection intersection, Point p){        intersectionPanel = new IntersectionPanel(intersection);        initComponents("Intersection: ", constructIntersectionMessage(intersection), new Point(0,0), intersectionPanel);	}		public MessageWindow(Gateway gateway, GatewayMiniStatExt gatewayMiniStatExt, Point p){		initComponents("Gateway", constructGatewayMessage(gateway, gatewayMiniStatExt), p, null);	}		private String constructGatewayMessage(Gateway gateway, GatewayMiniStatExt gatewayMiniStatExt){		StringBuilder stringBuilder = new StringBuilder();				String text = "<html>" + "Gateway name: " + gateway.getId() + "<br>"				+ "Measure: "+gateway.getMeasure() + "<br>";				stringBuilder.append(text);				RouteStat routeStat = gatewayMiniStatExt.getRouteStat(gateway);		if(routeStat != null){			stringBuilder.append("Standard dev duration: ").append(routeStat.getStdDevDuration()).append("<br>");		}				stringBuilder.append("</html>");		return stringBuilder.toString();	}		private String constructIntersectionMessage(Intersection intersection){		return "<html>" + "Intersection name: " + intersection.getId() + "<br>"				+ "Measure: "+intersection.getMeasure() + "<br>"				+ "</html>";	}		private String constructInfoMessage(Link link, LinkMiniStatExt miniStat){		String streetName = link.getStreetName() != null ? link.getStreetName() : "Not defined" + "<br>";		return "<html>" + "Street name: " + streetName + "<br>"											+ "Length :"+link.getLength() + "<br>"											+ "Current load: " + RealSimulationParams.convertToKPH((float)link.getLoad()) + "<br>"											+ "Velocity limit: " + link.getSpeedLimit()+ "<br>"											+ "Current load: " + link.getLoad() + "<br>"											+ "Direction: " + link.getDirection() + "<br>"											+ "From: " + link.getBeginning().getSubGraphNumber() + " to " + link.getEnd().getSubGraphNumber() + "<br>"											+ "Link Number: "+ link.getLinkNumber() + "<br>"											+ "Car count: "+ miniStat.getCarCount() + "<br>"											+ "Car drive count: "+ miniStat.getDriveCount() + "<br>"											+ "Avarage duration: "+ miniStat.getAvgDuration() + "<br>"											+ "Avarage velocity: " + RealSimulationParams.convertToKPH(miniStat.getAvgVelocity() )+ " km/h<br>"											+ "Avarage riding velocity: " + RealSimulationParams.convertToKPH((float)miniStat.getAvarageRidingVelocity())+ " km/h<br>"											+ "Car waiting on red light: " + miniStat.getCarCountOnRedLigth()											+ "</html>";	}	private void initComponents(String title, String message, Point p, IntersectionPanel optionalPanel){		setTitle(title);		JPanel panel = new JPanel();        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));		JLabel label = new JLabel(message);		panel.add(label);		add(panel);        if(optionalPanel != null){            add(optionalPanel);            optionalPanel.setLabel(label);        }		setAlwaysOnTop(true);		setLocation(p);		pack();	}    public void update() {        if (intersectionPanel != null){            intersectionPanel.update();        }    }}