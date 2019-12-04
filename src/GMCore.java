import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GMCore implements ActionListener, KeyListener {
	public JFrame frame = new JFrame();
	Timer timer = new Timer(1000 / 10, this);
	JPanel panelsPanel = new JPanel();
	JPanel mainPanel = new JPanel();
	JPanel timePanel = new JPanel();
	JPanel savePanel = new JPanel();
	JPanel eventPanel = new JPanel();
	JLabel partyDateLabel = new JLabel();
	JLabel earthDateLabel = new JLabel();
	JComboBox<String> eventsBox = new JComboBox<String>();

	JLabel eventsPanelToggleLabel = new JLabel("Show Events Panel");
	JCheckBox eventsPanelToggle = new JCheckBox();

	JLabel daysAdvancedLabel = new JLabel("Days");
	JLabel hoursAdvancedLabel = new JLabel("Hours");
	JLabel minutesAdvancedLabel = new JLabel("Minutes");
	JTextField daysAdvanced = new JTextField("0");
	JTextField hoursAdvanced = new JTextField("0");
	JTextField minutesAdvanced = new JTextField("0");
	JTextField convertField = new JTextField("0");

	// Conversion stuff
	JPanel conversionPanel = new JPanel();
	String[] convertStrings = { "Light Second", "Light Minute", "Light Hour", "Light Day", "Light Year" };
	JComboBox<String> convertBox = new JComboBox<String>(convertStrings);
	JLabel convertResultLabel = new JLabel();

	JLabel velocityLabel = new JLabel("Velocity");
	JTextField kinematicRelativityInput = new JTextField("0");
	String[] kinematicRelativityStrings = { "Percent C", "km/s" };
	JComboBox<String> kinematicRelativityTypeBox = new JComboBox<String>(kinematicRelativityStrings);

	JLabel massLabel = new JLabel("Stellar Mass (Earths)");
	JLabel distanceLabel = new JLabel("Distance (km)");
	JTextField gravimetricMass = new JTextField("0");
	JTextField gravimetricDistance = new JTextField("0");
	JLabel ssRadiusLabel = new JLabel("SS Radius: ");

	JButton timeAdvanceButton = new JButton("Advance");
	JButton saveButton = new JButton("Save");
	JButton loadButton = new JButton("Load");
	JButton addEventButton = new JButton("+");

	Dimension fullDim = new Dimension(270, 600);
	Dimension fullWidthBarDim = new Dimension(250, 25);
	Dimension eventsBoxDim = new Dimension(210, 25);
	Dimension timeAdvanceDim = new Dimension(80, 30);
	Dimension timeAdvanceLabelDim = new Dimension(80, 20);
	Dimension halfDim = new Dimension(122, 30);

	static Calendar partyCalendar;
	static Calendar earthCalendar;

	static ArrayList<SREvent> events = new ArrayList<SREvent>();

	DecimalFormat decimalFormat = new DecimalFormat("#.##");
	boolean isDisplayingEventsInputPanel = false;

	public static void main(String[] args) {
		GMCore core = new GMCore();
		core.start();
	}

	private void start() {
		frame.add(panelsPanel);
		panelsPanel.add(mainPanel);
		mainPanel.add(timePanel);
		mainPanel.add(savePanel);
		mainPanel.add(conversionPanel);
		mainPanel.setBackground(new Color(200, 150, 150));

		savePanel.add(saveButton);
		savePanel.add(loadButton);
		savePanel.setBackground(new Color(150, 200, 150));

		// Date stuff
		timePanel.add(partyDateLabel);
		timePanel.add(earthDateLabel);
		timePanel.add(eventsBox);
		timePanel.add(addEventButton);
		timePanel.add(eventsPanelToggleLabel);
		timePanel.add(eventsPanelToggle);
		timePanel.setBackground(new Color(150, 150, 200));
		eventPanel.setBackground(new Color(200, 200, 200));

		panelsPanel.setPreferredSize(fullDim);
		mainPanel.setPreferredSize(fullDim);
		timePanel.setPreferredSize(new Dimension(270, 400));
		savePanel.setPreferredSize(new Dimension(270, 50));
		conversionPanel.setPreferredSize(new Dimension(270, 100));
		eventPanel.setPreferredSize(fullDim);
		partyDateLabel.setPreferredSize(fullWidthBarDim);
		earthDateLabel.setPreferredSize(fullWidthBarDim);

		eventsBox.setPreferredSize(eventsBoxDim);
		addEventButton.setFont(new Font("Arial", Font.BOLD, 20));
		addEventButton.setPreferredSize(new Dimension(30, 25));
		eventsPanelToggleLabel.setPreferredSize(new Dimension(225, 25));
		eventsPanelToggle.setPreferredSize(new Dimension(25, 25));

		// Time Advancement stuff
		timePanel.add(daysAdvancedLabel);
		timePanel.add(hoursAdvancedLabel);
		timePanel.add(minutesAdvancedLabel);
		timePanel.add(daysAdvanced);
		timePanel.add(hoursAdvanced);
		timePanel.add(minutesAdvanced);

		addDivider(timePanel, 250);

		timePanel.add(velocityLabel);
		timePanel.add(kinematicRelativityInput);
		timePanel.add(kinematicRelativityTypeBox);

		addDivider(timePanel, 250);

		timePanel.add(massLabel);
		timePanel.add(distanceLabel);
		timePanel.add(gravimetricMass);
		timePanel.add(gravimetricDistance);
		timePanel.add(ssRadiusLabel);

		addDivider(timePanel, 250);

		timePanel.add(timeAdvanceButton);

		addDivider(timePanel, 250);

		daysAdvancedLabel.setPreferredSize(timeAdvanceLabelDim);
		hoursAdvancedLabel.setPreferredSize(timeAdvanceLabelDim);
		minutesAdvancedLabel.setPreferredSize(timeAdvanceLabelDim);
		daysAdvancedLabel.setHorizontalAlignment(JLabel.CENTER);
		hoursAdvancedLabel.setHorizontalAlignment(JLabel.CENTER);
		minutesAdvancedLabel.setHorizontalAlignment(JLabel.CENTER);

		daysAdvanced.setPreferredSize(timeAdvanceDim);
		hoursAdvanced.setPreferredSize(timeAdvanceDim);
		minutesAdvanced.setPreferredSize(timeAdvanceDim);

		velocityLabel.setPreferredSize(fullWidthBarDim);
		kinematicRelativityInput.setPreferredSize(halfDim);
		kinematicRelativityTypeBox.setPreferredSize(new Dimension(122, 29));

		massLabel.setPreferredSize(halfDim);
		distanceLabel.setPreferredSize(halfDim);
		gravimetricMass.setPreferredSize(halfDim);
		gravimetricDistance.setPreferredSize(halfDim);

		// Conversion Panel stuff
		conversionPanel.add(convertField);
		conversionPanel.add(convertBox);
		conversionPanel.add(convertResultLabel);
		conversionPanel.setBackground(new Color(55, 200, 55));
		convertBox.setPreferredSize(new Dimension(122, 29));
		convertField.setPreferredSize(halfDim);
		convertResultLabel.setPreferredSize(fullWidthBarDim);
		convertBox.addActionListener(this);

		mainPanel.addKeyListener(this);
		eventsBox.addActionListener(this);
		eventsPanelToggle.addActionListener(this);
		timeAdvanceButton.setPreferredSize(fullWidthBarDim);
		timeAdvanceButton.addActionListener(this);
		kinematicRelativityTypeBox.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		gravimetricMass.addActionListener(this);
		addEventButton.addActionListener(this);

		initializeDates();
		initializeEvents();

		populateEventBox();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer.start();

		decimalFormat.setGroupingUsed(true);
		decimalFormat.setGroupingSize(3);
	}

	private void addDivider(JComponent c, int width) {
		JSeparator divider = new JSeparator(SwingConstants.HORIZONTAL);
		divider.setPreferredSize(new Dimension(width, 2));
		c.add(divider);
	}

	public void createEventPanel(SREvent e) {
		eventPanel.removeAll();
		JLabel nameLabelText = new JLabel("EVENT NAME");
		nameLabelText.setPreferredSize(fullWidthBarDim);
		eventPanel.add(nameLabelText);

		JLabel nameLabel = new JLabel(e.name);
		nameLabel.setPreferredSize(fullWidthBarDim);
		eventPanel.add(nameLabel);

		addDivider(eventPanel, 250);

		JLabel dateLabelText = new JLabel("DATE");
		dateLabelText.setPreferredSize(fullWidthBarDim);
		eventPanel.add(dateLabelText);

		JLabel dateLabel = new JLabel(getFormattedDateTime(e.date));
		dateLabel.setPreferredSize(fullWidthBarDim);
		eventPanel.add(dateLabel);

		addDivider(eventPanel, 250);

		JLabel descriptionLabelText = new JLabel("DESCRIPTION");
		descriptionLabelText.setPreferredSize(fullWidthBarDim);
		eventPanel.add(descriptionLabelText);

		JLabel descriptionLabel = new JLabel(e.description);
		descriptionLabel.setPreferredSize(fullWidthBarDim);
		eventPanel.add(descriptionLabel);
	}

	public void createEventInputPanel() {
		eventPanel.removeAll();
		JLabel nameLabelText = new JLabel("EVENT NAME");
		nameLabelText.setPreferredSize(fullWidthBarDim);
		eventPanel.add(nameLabelText);

		JTextField nameLabel = new JTextField();
		nameLabel.setPreferredSize(fullWidthBarDim);
		eventPanel.add(nameLabel);

		addDivider(eventPanel, 250);

		JLabel dateLabelText = new JLabel("DATE");
		dateLabelText.setPreferredSize(fullWidthBarDim);
		eventPanel.add(dateLabelText);

		JTextField dateLabel = new JTextField();
		dateLabel.setPreferredSize(fullWidthBarDim);
		eventPanel.add(dateLabel);

		addDivider(eventPanel, 250);

		JLabel descriptionLabelText = new JLabel("DESCRIPTION");
		descriptionLabelText.setPreferredSize(fullWidthBarDim);
		eventPanel.add(descriptionLabelText);

		JTextField descriptionLabel = new JTextField();
		descriptionLabel.setPreferredSize(fullWidthBarDim);
		eventPanel.add(descriptionLabel);
		
		JButton addEvent = new JButton("Add Event");
		addEvent.setPreferredSize(timeAdvanceDim);
		addEvent.addActionListener(this);
		eventPanel.add(addEvent);
	}

	public void addEvent(String name, Calendar date, String description) {
		events.add(new SREvent(name, date, description));
	}

	private static void saveData(String fileName, Object data) {
		try (FileOutputStream fos = new FileOutputStream(new File(fileName));
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(data);
			System.out.println(fileName.replace("src/", "") + " saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Object loadData(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object data = ois.readObject();
			ois.close();
			System.out.println(fileName.replace("src/", "") + " loaded successfully!");
			return data;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// This can occur if the object we read from the file is not
			// an instance of any recognized class
			e.printStackTrace();
			return null;
		}
	}

	private void initializeEvents() {

		Calendar crash = Calendar.getInstance();
		crash.set(2073, 7, 1, 14, 33, 50);
		Calendar eruption = Calendar.getInstance();
		eruption.set(2073, 7, 1, 14, 33, 50);
		events.add(new SREvent("Crash", crash, "The first death event"));
		events.add(new SREvent("Eruption", eruption, "Exploding mountains!"));
	}

	// ----------------------------DATES----------------------------
	private void initializeDates() {
		if (earthCalendar == null || partyCalendar == null) {
			partyCalendar = Calendar.getInstance();
			earthCalendar = Calendar.getInstance();
			partyCalendar.set(2073, 7, 21, 4, 0, 0);
			earthCalendar.set(2073, 7, 28, 1, 53, 31);
		}
		updateDateDisplay();
	}

	public static void loadDates() {
		System.out.println("Loading dates...");
		Calendar[] calendars = (Calendar[]) loadData("src/dates.dat");
		partyCalendar = calendars[0];
		earthCalendar = calendars[1];
	}

	public static boolean loadEvents() {
		System.out.println("Loading events...");
		events = (ArrayList<SREvent>) loadData("src/events.dat");
		return true;
	}

	private void updateDateDisplay() {
		partyDateLabel.setText("Party Date: " + getFormattedDateTime(partyCalendar));
		earthDateLabel.setText("Earth Date: " + getFormattedDateTime(earthCalendar));
	}

	private void updateSSRadiusDisplay() {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		if (pattern.matcher(gravimetricMass.getText()).matches()) {
			double d = SpecialRelativity.getSchwarzschildUsingEscapeVelocity(
					Double.parseDouble(gravimetricMass.getText()) * SpecialRelativity.earthMass);
			ssRadiusLabel.setText("SS Radius: " + String.format("%.4f", d) + " km");
		}
	}

	private void updateConvertDisplay() {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		BigDecimal conversionRate = BigDecimal.ONE;
		BigDecimal ls = new BigDecimal(299792, SpecialRelativity.hpmc);
		switch ((String) convertBox.getSelectedItem()) {
		case "Light Second":
			conversionRate = ls;
			break;
		case "Light Minute":
			conversionRate = ls.multiply(new BigDecimal(60, SpecialRelativity.hpmc), SpecialRelativity.hpmc);
			break;
		case "Light Hour":
			conversionRate = ls.multiply(new BigDecimal(3600, SpecialRelativity.hpmc), SpecialRelativity.hpmc);
			break;
		case "Light Day":
			conversionRate = ls.multiply(new BigDecimal(3600 * 24, SpecialRelativity.hpmc), SpecialRelativity.hpmc);
			break;
		case "Light Year":
			conversionRate = ls.multiply(new BigDecimal(3600 * 24 * 365, SpecialRelativity.hpmc),
					SpecialRelativity.hpmc);
			break;
		default:
			conversionRate = BigDecimal.ONE;
			break;
		}
		if (pattern.matcher(convertField.getText()).matches()) {
			double count = Double.parseDouble(convertField.getText());
			BigDecimal result = conversionRate.multiply(new BigDecimal(count, SpecialRelativity.hpmc),
					SpecialRelativity.hpmc);
			double d = result.doubleValue();
			convertResultLabel.setText(decimalFormat.format(d) + " km");
		}
	}

	private String getFormattedDateTime(Calendar c) {
		String time = c.getTime().toString();
		return time;
	}

	// ----------------------------EVENTS----------------------------
	private void populateEventBox() {
		eventsBox.removeAllItems();
		String[] eventNames = new String[events.size()];
		for (int i = 0; i < events.size(); i++) {
			eventNames[i] = events.get(i).name;
			eventsBox.addItem(eventNames[i]);
		}
	}

	private double calculateTimeCoefficient(double t, double g) {
		double c = (t + g) - 1;
		return c;
	}

	private void advanceTimeByMillis(Calendar cal, double kineticCoefficient, double gravCoefficient) {
		double timeCoefficient = calculateTimeCoefficient(kineticCoefficient, gravCoefficient);
		if (cal == partyCalendar) {
			System.out.println("---BEGINNING TIME ADJUSTMENT---");
		} else {
			System.out.println();
			System.out.println("---Earth Time Adjustment---");
		}
		long millis = (long) (getMillisToAdvance() * timeCoefficient);
		long currentTime = cal.getTimeInMillis();
		System.out.println("TC: " + timeCoefficient + ", KC: " + kineticCoefficient + ", GC: " + gravCoefficient);
		System.out.println("Current Time is " + currentTime + ", Advancing by " + millis + " milliseconds.");
		printDate(cal);
		cal.setTimeInMillis(currentTime + millis);
		System.out.println("-----ADVANCING-----");
		printDate(cal);

		advanceNextCalendarBD(cal);
		updateDateDisplay();

		minutesAdvanced.setText("0");
		hoursAdvanced.setText("0");
		daysAdvanced.setText("0");
	}

	private void advanceNextCalendarBD(Calendar cal) {
		if (cal == partyCalendar) {
			double kinetic = 1;
			BigDecimal gravitational = BigDecimal.ONE;
			if (kinematicRelativityTypeBox.getSelectedIndex() == 0) {
				kinetic = SpecialRelativity
						.getKinematicTimeDilationByPercentC(Double.parseDouble(kinematicRelativityInput.getText()));
			} else if (kinematicRelativityTypeBox.getSelectedIndex() == 1) {
				kinetic = SpecialRelativity
						.getKinematicTimeDilationByVelocity(Double.parseDouble(kinematicRelativityInput.getText()));
			}
			double gMass = Double.parseDouble(gravimetricMass.getText());
			double gDist = Double.parseDouble(gravimetricDistance.getText());
			if (gMass != 0 && gDist != 0) {
				gravitational = SpecialRelativity.getGravimetricTimeDilationCoefficientBD(gDist, gMass);
			}
			System.out.println(gravitational);
			advanceTimeByMillis(earthCalendar, kinetic, gravitational.doubleValue());
		}
	}

	private long getMillisToAdvance() {
		long minuteToMillis = 60 * 1000;
		long hourToMillis = 60 * minuteToMillis;
		long dayToMillis = 24 * hourToMillis;

		long daysInMillis = Integer.parseInt(daysAdvanced.getText()) * dayToMillis;
		long hoursInMillis = Integer.parseInt(hoursAdvanced.getText()) * hourToMillis;
		long minutesInMillis = Integer.parseInt(minutesAdvanced.getText()) * minuteToMillis;

		long millis = daysInMillis + hoursInMillis + minutesInMillis;
		return millis;
	}

	private void printDate(Calendar cal) {
		int milli = cal.get(Calendar.MILLISECOND);
		int second = cal.get(Calendar.SECOND);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		System.out.println("Time is now: " + (month + 1) + "/" + day + "/" + year + ", " + hour + ":" + minute + ":"
				+ second + "." + milli);
		System.out.println(getFormattedDateTime(cal));
	}

	boolean isLeapYear(int year) {
		boolean leap = false;
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0)
					leap = true;
				else
					leap = false;
			} else
				leap = true;
		} else
			leap = false;
		return leap;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() instanceof JButton) {
			JButton pressed = (JButton)e.getSource();
			if (pressed == timeAdvanceButton) {
				advanceTimeByMillis(partyCalendar, 1, 1);
			}
			if (pressed == saveButton) {
				// saveDates();
				Calendar[] data = { partyCalendar, earthCalendar };
				saveData("src/dates.dat", data);
				saveData("src/events.dat", events);
			}
			if (pressed == loadButton) {
				loadDates();
				loadEvents();
				updateDateDisplay();
			}
			if(pressed.getText().equals("Add Event")) {
				
			}
			if (pressed == addEventButton) {
				isDisplayingEventsInputPanel = !isDisplayingEventsInputPanel;
				if (isDisplayingEventsInputPanel) {
					panelsPanel.setPreferredSize(new Dimension(fullDim.width * 2 + 10, fullDim.height));
					createEventInputPanel();
					panelsPanel.add(eventPanel);
				}
				else {
					panelsPanel.setPreferredSize(fullDim);
					panelsPanel.remove(eventPanel);
					eventsPanelToggle.setSelected(false);
				}
			}
		}
		

		if (e.getSource() == eventsPanelToggle) {
			isDisplayingEventsInputPanel = false;
			if (eventsPanelToggle.isSelected()) {
				panelsPanel.setPreferredSize(new Dimension(fullDim.width * 2 + 10, fullDim.height));
				createEventPanel(events.get(eventsBox.getSelectedIndex()));
				panelsPanel.add(eventPanel);
			} else {
				panelsPanel.setPreferredSize(fullDim);
				panelsPanel.remove(eventPanel);
			}
		}

		if (e.getSource() == eventsBox && eventsPanelToggle.isSelected()) {
			panelsPanel.remove(eventPanel);
			createEventPanel(events.get(eventsBox.getSelectedIndex()));
			panelsPanel.add(eventPanel);
		}

		if (e.getSource() == convertBox) {
			updateConvertDisplay();
		}

		if (e.getSource() == timer) {
			updateSSRadiusDisplay();
			updateConvertDisplay();
		}
		frame.pack();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
