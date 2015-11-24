package amdb.client;

import amdb.shared.Movie;
import amdb.shared.MovieCollection;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ChartType;
import com.googlecode.gwt.charts.client.ChartWrapper;
import com.googlecode.gwt.charts.client.controls.Dashboard;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.PieChart;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
import com.googlecode.gwt.charts.client.table.Table;
import com.googlecode.gwt.charts.client.table.TableOptions;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Moviebase implements EntryPoint {

	final DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Unit.EM);
	final MenuBar headerMenu = new MenuBar();
	final StackLayoutPanel splitLayoutPanel = new StackLayoutPanel(Unit.EM);
	final StackLayoutPanel splitLayoutPanel2 = new StackLayoutPanel(Unit.EM);
	private PushButton export = new PushButton("Export this view");
	private PushButton updateCountry = new PushButton("Update Chart");
	private PushButton updateLanguage = new PushButton("Update Chart");
	private PushButton updateGenre = new PushButton("Update Chart");
	private PushButton updateMinLength = new PushButton("Update Chart");
	private PushButton delete = new PushButton("Delete the chosen filter");
	private Tree filterTree = new Tree();
	private MovieCollection dataBase; // should not be changed
	private MovieCollection currentMovies;

	private DateRangeFilter dateRangeFilter;
	private Dashboard dashboard;
	private ChartWrapper<TableOptions> tableWrapper; 
	private GeoChart geoChart;
	private Table movieTable;
	private PieChart pieChart;
	private ColumnChart columnChart;
	private ListBox listBoxForCountries;
	private ListBox listBoxForLanguages;
	private ListBox listBoxForGenres;
	private TextBox textBoxForMinLength = new TextBox();
	private String[] countries;
	private String[] languages;
	private String[] genres;


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
		minLengthSort.setText("Filter By minimum Length");
		minLengthSort.addItem(textBoxForMinLength);
		minLengthSort.addItem(updateMinLength);

		TreeItem exportButtonSort = new TreeItem(new PushButton("Export this view"));

		//Add everything to the rootTree
		filterTree.addItem(countrySort);
		filterTree.addItem(languageSort);
		filterTree.addItem(genreSort);
		filterTree.addItem(minLengthSort);
		filterTree.addItem(delete);
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
		Command barDiagramCmd = new Command(){
			public void execute() {
				setColumnChart();
			}
		};

		//Menu Bar for the header and names for stylechanges
		MenuBar homeMenu = new MenuBar(true);
		MenuBar aboutUsMenu	= new MenuBar(true);
		MenuBar pieChartViewMenu = new MenuBar(true);
		MenuBar tableViewMenu = new MenuBar(true);
		MenuBar columnChartViewMenu = new MenuBar(true);
		MenuBar informationBackground = new MenuBar(true);
		MenuBar helpPage = new MenuBar(true);
		headerMenu.addItem("Worldmap",homeMenu);
		headerMenu.addItem("Pie Chart", pieChartViewMenu);
		headerMenu.addItem("Table", tableViewMenu);
		headerMenu.addItem("Column Chart",columnChartViewMenu);
		headerMenu.addItem("About Us", aboutUsMenu);
		headerMenu.addItem("Sources", informationBackground);
		headerMenu.addItem("Help",helpPage);

		//Add commands to MenuItems
		tableViewMenu.addItem("Change to Table", tableViewCmd);
		homeMenu.addItem("Change to Worldmap",homeMenuCmd);
		pieChartViewMenu.addItem("Change to Pie Chart", pieChartCmd);
		columnChartViewMenu.addItem("Change to Column Chart", barDiagramCmd);

		headerMenu.setStyleName("headerMenu",false);
		headerMenu.setStyleName("homeMenu",false);
		headerMenu.setStyleName("databaseMenu",false);
		headerMenu.setStyleName("aboutUsMenu",false);

		/*******************************************************************/

		//Defines the Panel for Menu Sidebar
		splitLayoutPanel.add(filterTree, new HTML("Filter Options"), 5);
		splitLayoutPanel.getHeaderWidget(filterTree).addStyleName("filteroptionsheader");
		splitLayoutPanel.setStyleName("sidebar",false);

		/*******************************************************************/

		//Rootpanel where anything else is include
		dockLayoutPanel.addNorth(headerMenu, 3);
		dockLayoutPanel.addSouth(new HTML("South"), 4);
		//dockLayoutPanel.addEast(new HTML("East"), 7);
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
	
	// create DateRangeFilter and add it to South-panel
	/**
	 * This method creates a <tt>DateRangeFilter</tt> and adds it to the South-panel.
	 */
//	public void setDateRangeFilter(){
//		ChartLoader chartLoader = new ChartLoader(ChartPackage.CONTROLS);
//		chartLoader.loadApi(new Runnable() {
//			@Override
//			public void run() {
//				dateRangeFilter = new DateRangeFilter();
//				tableWrapper = new ChartWrapper<TableOptions>();
//				tableWrapper.setChartType(ChartType.TABLE);		// create one method for each diagram form
//				dockLayoutPanel.addSouth(dashboard, 100);
//				dockLayoutPanel.addSouth(dateRangeFilter, 4);
//				dockLayoutPanel.add(tableWrapper);
//				DateRangeFilter.draw(dateRangeFilter, currentMovies);
//			}
//		});	
//	}
//	
	// create Map, remove the current center, add Map to center
	public void setMap(){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				geoChart = new GeoChart();
				dockLayoutPanel.remove(3);
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
				dockLayoutPanel.remove(3);
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
				dockLayoutPanel.remove(3);
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
					dockLayoutPanel.remove(3);
					dockLayoutPanel.add(columnChart);
					ColumnChartComponent.drawColumnChart(columnChart, currentMovies);
				}
			});	
		}
		
	}
	
	/**
	 * This method redraws the component that is currently displayed in the dockLayoutPanel
	 */
	private void redrawMainComponent(){
		if(dockLayoutPanel.getWidget(3) == geoChart){
			setMap();
		}else if(dockLayoutPanel.getWidget(3) == movieTable){
			setTable();
		}else if(dockLayoutPanel.getWidget(3) == pieChart){
			setPieChart();
		}else if(dockLayoutPanel.getWidget(3) == columnChart){
			setColumnChart();
		}
	}

	//Deletes the chosen filter depending on the current Center
	public void deleteFilter(){
		
		currentMovies = dataBase;
		updateFilterSelectBox();
		
		redrawMainComponent();
		
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
	

	
	
}

