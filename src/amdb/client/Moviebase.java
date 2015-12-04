package amdb.client;

import java.util.ArrayList;

import amdb.client.slider.RangeSlider;
import amdb.client.slider.SliderEvent;
import amdb.client.slider.SliderListener;
import amdb.shared.Movie;
import amdb.shared.MovieCollection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;

import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.table.Table;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Moviebase implements EntryPoint {

	final DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
	final MenuBar headerMenu = new MenuBar();
	final StackLayoutPanel splitLayoutPanel = new StackLayoutPanel(Unit.EM);
	final StackLayoutPanel splitLayoutPanel2 = new StackLayoutPanel(Unit.EM);
	final VerticalPanel verticalSouthPanel = new VerticalPanel();

	private PushButton export = new PushButton("Export This View");
	private PushButton updateCountry = new PushButton("Update Chart");
	private PushButton updateLanguage = new PushButton("Update Chart");
	private PushButton updateGenre = new PushButton("Update Chart");
	private PushButton updateMinLength = new PushButton("Update Chart");
	private PushButton updateMinAndMaxYear = new PushButton("Update Chart");
	private PushButton delete = new PushButton("Delete Chosen Filter");
	private PushButton displayPerCapita = new PushButton("Display Per Capita");
	private Tree filterTree = new Tree();
	private MovieCollection dataBase; // should not be changed
	private MovieCollection currentMovies;

	private GeoChart geoChart;
	private Table movieTable;
	private PieChart pieChart;
	private ColumnChart columnChart;
	private ListBox listBoxForCountries;
	private ListBox listBoxForLanguages;
	private ListBox listBoxForGenres;
	private TextBox textBoxForMinLength = new TextBox();
	private TextBox textBoxForMinYear = new TextBox();
	private TextBox textBoxForMaxYear = new TextBox();
	private String[] countries;
	private String[] languages;
	private String[] genres;
	private RangeSlider s;
	private final HTML sliderLabel = new HTML("Chosen Range: [1888, 2016]");


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// set a value for MovieCollection database if possible

		setDatabase();		

		/*******************************************************************/
		//Builds the Tree for the Global Sort Options part

		//Create the listBoxes for the sidebar
		listBoxForCountries = new ListBox();
		listBoxForCountries.setWidth("215px");
		listBoxForLanguages = new ListBox();
		listBoxForLanguages.setWidth("215px");
		listBoxForGenres = new ListBox();
		listBoxForGenres.setWidth("215px");
		//listBoxes are filled in onDatabaseReady()
		/*******************************************************************/

		textBoxForMinLength.addKeyPressHandler(new KeyPressHandler() {

			public void onKeyPress(KeyPressEvent event) {
				if (!Character.isDigit(event.getCharCode())) {
					((TextBox) event.getSource()).cancelKey();
				}
			}
		});
		
		textBoxForMinYear.addKeyPressHandler(new KeyPressHandler(){
			
			public void onKeyPress(KeyPressEvent event){
				if(!Character.isDigit(event.getCharCode())){
					((TextBox)event.getSource()).cancelKey();
				}
			}
		});
		
		textBoxForMaxYear.addKeyPressHandler(new KeyPressHandler(){
			
			public void onKeyPress(KeyPressEvent event){
				if(!Character.isDigit(event.getCharCode())){
					((TextBox)event.getSource()).cancelKey();
				}
			}
		});
		
		
		setRangeSlider();

		
		Image image = new Image();
		image.setUrl(GWT.getModuleBaseURL()+"images/banana.gif");
		image.setSize("200px", "500px");

		//Build filterTree for country sorting
		TreeItem countrySort = new TreeItem();
		countrySort.setText("Filter By Country");
		countrySort.addItem(listBoxForCountries);
		countrySort.addItem(updateCountry);

		//Build filterTree for language sorting
		TreeItem languageSort = new TreeItem();
		languageSort.setText("Filter By Language");
		languageSort.addItem(listBoxForLanguages);
		languageSort.addItem(updateLanguage);

		//Build filterTree for genre sorting
		TreeItem genreSort = new TreeItem();
		genreSort.setText("Filter By Genre");
		genreSort.addItem(listBoxForGenres);
		genreSort.addItem(updateGenre);

		//Build filterTree for minLength sorting
		TreeItem minLengthSort = new TreeItem();
		minLengthSort.setText("Filter By Minimum Length");
		minLengthSort.addItem(textBoxForMinLength);
		minLengthSort.addItem(updateMinLength);

		TreeItem exportButtonSort = new TreeItem(export);

		//Add everything to the rootTree
		filterTree.addItem(countrySort);
		filterTree.addItem(languageSort);
		filterTree.addItem(genreSort);
		filterTree.addItem(minLengthSort);
		filterTree.addItem(delete);
		filterTree.addItem(displayPerCapita);
		filterTree.addItem(exportButtonSort);

		countrySort.setStyleName("countrySort",false);
		countrySort.setState(true);

		languageSort.setStyleName("languageSort",false);
		languageSort.setState(true);

		genreSort.setStyleName("genreSort",false);
		genreSort.setState(true);

		minLengthSort.setStyleName("minLengthSort",false);
		minLengthSort.setState(true);

		exportButtonSort.setStyleName("exportButtonSort",false);
		countrySort.setState(true);

		//All clickevents for the buttons in the sidebar

		delete.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				deleteFilter();
			}
		});

		updateCountry.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateCountryChart(listBoxForCountries.getSelectedItemText());
			}
		});

		updateLanguage.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateLanguageChart(listBoxForLanguages.getSelectedItemText());
			}
		});

		updateGenre.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateGenreChart(listBoxForGenres.getSelectedItemText());
			}
		});

		updateMinLength.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateMinLengthChart(textBoxForMinLength.getValue());
			}
		});
		
		updateMinAndMaxYear.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateMinAndMaxYearChart(textBoxForMinYear.getValue(), textBoxForMaxYear.getValue());
			}
		});
		
		displayPerCapita.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				displayMapPerCapita();
			}
		});

		export.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// ensure, that currently displayed window is a chart that contains a SGV
				if(dockLayoutPanel.getWidget(4) == geoChart || dockLayoutPanel.getWidget(4) == pieChart || dockLayoutPanel.getWidget(4) == columnChart){
					Export.exportAsSVG();
				}else{
					HTML contentOfPopup = new HTML("<div style = \"height: 100px;    background-color: #FF0000; \">"
							+ "<p>Export for this view is currently not supported."
							+ "<p>Click anywhere outside this Window to close this message</div>");
				    final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
					simplePopup.setWidget(contentOfPopup);
					simplePopup.center();
					simplePopup.show();
				}
			}
		});
		
		/*******************************************************************/
		// have textboxes listen to keyboard events

		textBoxForMinLength.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getUnicodeCharCode() == KeyCodes.KEY_ENTER) {
					updateMinLengthChart(textBoxForMinLength.getValue());	
				}
			}
		});

		/*******************************************************************/
		//command to change to table view
		Command tableViewCmd = new Command() {
			public void execute() {
				setTable();
			}
		};
		//command to change to home menu
		Command homeMenuCmd = new Command(){
			public void execute() {
				setMap();
			}
		};
		//command to change to pie chart
		Command pieChartCmd = new Command(){
			public void execute() {
				setPieChart();
			}
		};
		//command to change to bar diagram
		Command columnChartCmd = new Command(){
			public void execute() {
				setColumnChart();
			}
		};
		
		//command to change to about page
		Command aboutPageCmd = new Command() {
			public void execute() {
				setAboutPage();
			}
		};

		//Menu Bar for the header and names for stylechanges
		MenuBar homeMenu = new MenuBar(true);
		MenuBar aboutMenu	= new MenuBar(true);
		MenuBar pieChartViewMenu = new MenuBar(true);
		MenuBar tableViewMenu = new MenuBar(true);
		MenuBar columnChartViewMenu = new MenuBar(true);
		headerMenu.addItem("Worldmap",homeMenu);
		headerMenu.addItem("Pie Chart", pieChartViewMenu);
		headerMenu.addItem("Column Chart",columnChartViewMenu);
		headerMenu.addItem("Table", tableViewMenu);
		headerMenu.addItem("About", aboutMenu);

		//Add commands to MenuItems
		tableViewMenu.addItem("Change to Table", tableViewCmd);
		homeMenu.addItem("Change to Worldmap",homeMenuCmd);
		pieChartViewMenu.addItem("Change to Pie Chart", pieChartCmd);
		columnChartViewMenu.addItem("Change to Column Chart", columnChartCmd);
		aboutMenu.addItem("Change to About Page", aboutPageCmd);

		headerMenu.setStyleName("headerMenu",false);
		headerMenu.setStyleName("homeMenu",false);
		headerMenu.setStyleName("databaseMenu",false);
		headerMenu.setStyleName("aboutMenu",false);

		/*******************************************************************/

		//Defines the Panel for Menu Sidebar
		splitLayoutPanel.add(filterTree, new HTML("Filter Options"), 5);
		splitLayoutPanel.getHeaderWidget(filterTree).addStyleName("filteroptionsheader");
		splitLayoutPanel.setStyleName("sidebar",false);

		/*******************************************************************/

		//Rootpanel where anything else is include
		dockLayoutPanel.addNorth(headerMenu, 3);
		dockLayoutPanel.addSouth(verticalSouthPanel,6);
		dockLayoutPanel.addEast(image, 15);
		dockLayoutPanel.addWest(splitLayoutPanel,20);

		/*******************************************************************/

		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		rootPanel.add(dockLayoutPanel);

		/*******************************************************************/
		// create map and hang it into the central panel
		// this even works when database is null due to an error
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				// Create and attach the chart
				geoChart = new GeoChart();
				// attatch it to the approriate panel
				dockLayoutPanel.add(geoChart);
				MapComponent.drawMap(geoChart, dataBase);
			}
		});	





		/***********************************************************************************/
		/*****************************END**OF**ON-MODULE**LOAD******************************/
		/***********************************************************************************/
	}

	/**
	 * This method creates a <tt>RangeSlider</tt> on the vertical south panel to set filters by year.
	 * 
	 */
	public void setRangeSlider() {
		s = new RangeSlider("yearSlider",1888,2016,1888,2016);
		s.addListener(new SliderListener() {
			
			int currentMin=1888, currentMax = 2016;
			
			@Override
			public void onStop(SliderEvent e) {
			}
			
			@Override
			public void onStart(SliderEvent e) {
			}
			
			@Override
			public boolean onSlide(SliderEvent e) {
				int newcurrentMin=e.getValues()[0];
				int newcurrentMax=e.getValues()[1];
				if(newcurrentMin >= currentMin && currentMax >= newcurrentMax) {
					currentMin=e.getValues()[0];
					currentMax=e.getValues()[1];
					sliderLabel.setText("Chosen Range: ["+e.getValues()[0]+", "+e.getValues()[1]+"]");
					return true;
				} else return false;
			}
			
			@Override
			public void onChange(SliderEvent e) {
				updateMinAndMaxYearChart(""+e.getValues()[0], ""+e.getValues()[1]);
			}
			
		});
		s.setWidth("1000px");
		verticalSouthPanel.add(s);
		verticalSouthPanel.add(sliderLabel);
		verticalSouthPanel.setStyleName("veticalSouthPanel");
		verticalSouthPanel.setSpacing(15);
	}

	/**
	 * Calls on the server to send (and if necessary parse from file) the Movie Database and assigns the received value to database.
	 * 
	 * @pre true
	 * @post database != null || alert given
	 */
	public void setDatabase() {
		GWT.log("Fetching movies");
		try {
			// call on server to request the file in the specified path
			// request regular file
//			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "PreprocessedData/movies_preprocessed.tsv");
			// request systemtest file 
//			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "PreprocessedData/systemtest_file.tsv");
			// request files from directory
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "PreprocessedData/movies_preprocessed_dir.tsv");			
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					GWT.log("Request failed.");
				}
				public void onResponseReceived(Request request,	Response response) {
					if (200 == response.getStatusCode()) {
						GWT.log("Response successfull.");
						// convert the received file to a MovieCollection
						dataBase = ParserClientside.stringToMovieCollection(response.getText());
						currentMovies = dataBase;
						GWT.log("Movies loaded.");
						onDatabaseReady();
					} else {
						GWT.log("Response failed.");
					}
				}
			});
		} catch (RequestException e) {
			GWT.log("Request failed.");
		}
	}		


	/**
	 * This method is executed after setting the database finished.
	 * It contains code that depends on the database being fully loaded.
	 * 
	 * @pre database != null
	 * @post true
	 */
	public void onDatabaseReady(){
		updateFilterSelectBox();
	}

	/**
	 * This method changes the FilterSelectBoxes to only display possible options.
	 * 
	 * @pre database != null
	 * @post only valid options are displayed
	 */
	private void updateFilterSelectBox(){
		countries = currentMovies.getAllCountries();
		languages = currentMovies.getAllLanguages();
		genres = currentMovies.getAllGenres();

		//Fill the two listBoxes for the sidebar
		listBoxForCountries.clear();
		for(int i = 0; i < countries.length; i++){
			listBoxForCountries.addItem(countries[i]);
		}
		listBoxForLanguages.clear();
		for(int i = 0; i < languages.length; i++){
			listBoxForLanguages.addItem(languages[i]);
		}
		listBoxForGenres.clear();
		for (int i = 0; i < genres.length; i++) {
			listBoxForGenres.addItem(genres[i]);
		}
	}
	
	// create Map, remove the current center, add Map to center
	public void setMap(){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				geoChart = new GeoChart();
				dockLayoutPanel.remove(4);
				dockLayoutPanel.add(geoChart);
				MapComponent.drawMap(geoChart, currentMovies);
			}
		});	
	}

	/**
	 * This method creates a <tt>Table</tt>, removes the current centre and replaces 
	 * it by the <tt>Table</tt>.
	 */
	public void setTable(){
		ChartLoader tableLoader = new ChartLoader(ChartPackage.TABLE);
		tableLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				movieTable = new Table();
				dockLayoutPanel.remove(4);
				dockLayoutPanel.add(movieTable);
				TableComponent.draw(movieTable, currentMovies);
			}
		});
	}

	// create Pie Chart, remove the current center, add Pie Chart to center
	public void setPieChart(){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				pieChart = new PieChart();
				dockLayoutPanel.remove(4);
				dockLayoutPanel.add(pieChart);
				PieChartComponent.drawPieChart(pieChart, currentMovies);
			}
		});	
	}

	// create Column Chart, remove the current center, add Column Chart to center
	public void setColumnChart(){
		// Conversion of MovieCollection to DataTable
		ArrayList<Movie> movieList = currentMovies.getMovies();

		// The view can lock down the whole browser sometimes. Limiting the amount of movies fixes that.
		if (movieList.size() > 35000) {
			Window.alert("The chosen data sample is too large to display!");
			GWT.log("Data sample was too large!");
		} else {
			ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
			chartLoader.loadApi(new Runnable() {
				@Override
				public void run() {
					columnChart = new ColumnChart();
					dockLayoutPanel.remove(4);
					dockLayoutPanel.add(columnChart);
					ColumnChartComponent.drawColumnChart(columnChart, currentMovies);
				}
			});	
		}

	}
	
	/**
	 * This method removes the current centre and replaces it with the about page.
	 */
	public void setAboutPage(){
		Frame frame = new Frame();
		dockLayoutPanel.remove(4);
		dockLayoutPanel.add(frame);
		AboutPage.drawAboutPage(frame);
	}

	/**
	 * This method redraws the component that is currently displayed in the dockLayoutPanel
	 */
	private void redrawMainComponent(){
		if(dockLayoutPanel.getWidget(4) == geoChart){
			setMap();
		}else if(dockLayoutPanel.getWidget(4) == movieTable){
			setTable();
		}else if(dockLayoutPanel.getWidget(4) == pieChart){
			setPieChart();
		}else if(dockLayoutPanel.getWidget(4) == columnChart){
			setColumnChart();
		}
	}

	//Deletes the chosen filter depending on the current Center
	public void deleteFilter(){

		currentMovies = dataBase;
		updateFilterSelectBox();
		verticalSouthPanel.remove(s);
		verticalSouthPanel.remove(sliderLabel);
		redrawMainComponent();
		setRangeSlider();

	}

	//Update the chosen filter depending on the current Center
	public void updateCountryChart(String country){

		currentMovies = currentMovies.filterByCountry(country);
		updateFilterSelectBox();

		redrawMainComponent();
	}

	//Update the chosen filter depending on the current Center
	public void updateLanguageChart(String language){

		currentMovies = currentMovies.filterByLanguage(language);
		updateFilterSelectBox();

		redrawMainComponent();
	}

	public void updateGenreChart(String genre){

		currentMovies = currentMovies.filterByGenre(genre);
		updateFilterSelectBox();

		redrawMainComponent();
	}

	public void updateMinLengthChart(String minLength){

		int intMinLength = Integer.parseInt(minLength);
		currentMovies = currentMovies.filterByMinLength(intMinLength);
		updateFilterSelectBox();

		redrawMainComponent();
	}
	
	public void updateMinAndMaxYearChart(String minYear,String maxYear){
		
		int intMinYear = Integer.parseInt(minYear);
		int intMaxYear = Integer.parseInt(maxYear);
		currentMovies = currentMovies.filterByYear(intMinYear, intMaxYear);
		updateFilterSelectBox();
		
		redrawMainComponent();
		
	}
	
	public void displayMapPerCapita(){
		
		if(dockLayoutPanel.getWidget(4) == geoChart){
			geoChart = new GeoChart();
			dockLayoutPanel.remove(4);
			dockLayoutPanel.add(geoChart);
			PerCapitaComponent.drawPerCapita(geoChart, currentMovies);
		}
		else{
			Window.alert("This view is only available for the worldmap!");
		}
	}


}

