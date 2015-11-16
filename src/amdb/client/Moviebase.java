package amdb.client;

import amdb.client.MovieCollectionServiceInterface;
import amdb.client.MovieCollectionServiceInterfaceAsync;
import amdb.shared.MovieCollection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
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
	private PushButton export = new PushButton("Export this view");
	private PushButton acceptForMap = new PushButton("Accept this choice for Map");
	private PushButton acceptForPieChart = new PushButton("Accept this choice for Pie Chart");
	private PushButton acceptForTableChart = new PushButton("Accept this choice for Table Chart");
	private PushButton acceptForBarDiagram = new PushButton("Accept this choice for Bar Diagram");
	private PushButton acceptLanguageForTableChart = new PushButton("Accept this choice for Table Chart");
	private PushButton acceptLanguageForMap = new PushButton("Accept this choice for Map");
	private PushButton acceptLanguageForPieChart = new PushButton("Accept this choice for Pie Chart");
	private PushButton acceptLanguageForBarDiagram = new PushButton("Accept this choice for Bar Diagram");
	private PushButton delete = new PushButton("Delete the chosen filter");
	private Tree filterTreeForMap = new Tree();
	private MovieCollection dataBase; // should not be changed
	private GeoChart worldmap;
	private Table movieTable;
	private PieChart pieChart;
	private ColumnChart columnChart;
	private ListBox listBoxForCountries;
	private ListBox listBoxForLanguages;
	private String[] countries;
	private String[] languages;
	private final MovieCollectionServiceInterfaceAsync movieCollectionService = GWT.create(MovieCollectionServiceInterface.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		countries = new String[270];
		String [] countries = {"Argentina","Brazil","Canada","Denmark","France","German Democratic Republic","Germany",
				"Hong Kong","India","Italy","Japan","Mexico","Netherlands","New Zealand","Norway","South Africa",
				"Soviet Union","Switzerland","Turkey","United Kingdom","United States of America"};
		
		languages = new String[100];
		String [] languages = {"Afrikaans Language","Cantonese","Danish Language","Dutch Language","Englisch Language",
				"German Language","Hindi Language","Italian Language","Japanese Language","Malayalam Language",
				"Norwegian Language","Portugese Language","Russian Language","Silent film","Spanish Language",
				"Standard Cantonese","Standard Mandarin","Tamil Language","Turkish Language"};
		/*******************************************************************/
		//Builds the Tree for the Global Sort Options part
		
		//Fill the two listBoxes for the sidebar
		listBoxForCountries = new ListBox();
		for(int i = 0; i < countries.length; i++){
			listBoxForCountries.addItem(countries[i]);
		}
		listBoxForLanguages = new ListBox();
		for(int i = 0; i < languages.length; i++){
			listBoxForLanguages.addItem(languages[i]);
		}
		
		//Build filterTree for country sorting
		TreeItem countrySort = new TreeItem();
		countrySort.setText("Filter By Country");
		countrySort.addItem(listBoxForCountries);
		countrySort.addItem(acceptForMap);
		countrySort.addItem(acceptForPieChart);
		countrySort.addItem(acceptForTableChart);
		countrySort.addItem(acceptForBarDiagram);
		
		//Build filterTree for language sorting
		TreeItem languageSort = new TreeItem();
		languageSort.setText("Filter By Language");
		languageSort.addItem(listBoxForLanguages);
		languageSort.addItem(acceptLanguageForMap);
		languageSort.addItem(acceptLanguageForTableChart);
		languageSort.addItem(acceptLanguageForPieChart);
		languageSort.addItem(acceptLanguageForBarDiagram);
		
		TreeItem exportButtonSort = new TreeItem(new PushButton("Export this view"));
		
		//Add everything to the rootTree
		filterTreeForMap.addItem(countrySort);
		filterTreeForMap.addItem(languageSort);
		filterTreeForMap.add(delete);
		filterTreeForMap.addItem(exportButtonSort);
		
		countrySort.setStyleName("sort2",false);
		languageSort.setStyleName("sort3",false);
		exportButtonSort.setStyleName("exportButtonSort",false);
		
		//All clickevents for the buttons in the sidebar
		acceptForMap.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event) {
	    		changeVisibleCountriesForMap(dockLayoutPanel, listBoxForCountries.getSelectedItemText());
	    	}
	    });
		
		acceptForPieChart.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event) {
	    		changeVisibleCountriesForPieChart(dockLayoutPanel, listBoxForCountries.getSelectedItemText());
	    	}
	    });
		
		acceptForTableChart.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event) {
	    		changeVisibleCountriesForTable(dockLayoutPanel, listBoxForCountries.getSelectedItemText());
	    	}
	    });
		
		acceptForBarDiagram.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event) {
	    		changeVisibleCountriesForBarDiagram(dockLayoutPanel, listBoxForCountries.getSelectedItemText());
	    	}
	    });
		
		acceptLanguageForMap.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				changeVisibleLanguageForMap(dockLayoutPanel, listBoxForLanguages.getSelectedItemText());
			}
		});
		
		acceptLanguageForTableChart.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				changeVisibleLanguageForTableChart(dockLayoutPanel, listBoxForLanguages.getSelectedItemText());
			}
		});
		
		acceptLanguageForPieChart.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				changeVisibleLanguageForPieChart(dockLayoutPanel, listBoxForLanguages.getSelectedItemText());
			}
		});
		
		acceptLanguageForBarDiagram.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				changeVisibleLanguageForBarDiagram(dockLayoutPanel, listBoxForLanguages.getSelectedItemText());
			}
		});
		
		delete.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event) {
	    		deleteFilter(dockLayoutPanel,worldmap,pieChart,movieTable,columnChart);
	    	}
	    });
		
		/*******************************************************************/
		//command to change to table view
		Command tableViewCmd = new Command() {
			public void execute() {
				setTable(dockLayoutPanel);
			}
		};
		//command to change to home menu
		Command homeMenuCmd = new Command(){
			public void execute() {
				setHomeMenu(dockLayoutPanel);
			}
		};
		//command to change to pie chart
		Command pieChartCmd = new Command(){
			public void execute() {
				setPieChart(dockLayoutPanel);
			}
		};
		//command to change to bar diagram
		Command barDiagramCmd = new Command(){
			public void execute() {
				setColumnChart(dockLayoutPanel);
			}
		};

		//Menu Bar for the header and names for stylechanges
		MenuBar homeMenu = new MenuBar(true);
		MenuBar aboutUsMenu	= new MenuBar(true);
		MenuBar pieChartViewMenu = new MenuBar(true);
		MenuBar tableViewMenu = new MenuBar(true);
		MenuBar barDiagramViewMenu = new MenuBar(true);
		headerMenu.addItem("Home",homeMenu);
		headerMenu.addItem("Pie Chart", pieChartViewMenu);
		headerMenu.addItem("Table", tableViewMenu);
		headerMenu.addItem("Bar Diagram",barDiagramViewMenu);
		headerMenu.addItem("About Us", aboutUsMenu);
		
		//Add commands to MenuItems
		tableViewMenu.addItem("Change to Table", tableViewCmd);
		homeMenu.addItem("Change to Home Menu",homeMenuCmd);
		pieChartViewMenu.addItem("Change to Pie Chart", pieChartCmd);
		barDiagramViewMenu.addItem("Change to Bar Diagram", barDiagramCmd);

		headerMenu.setStyleName("headerMenu",false);
		headerMenu.setStyleName("homeMenu",false);
		headerMenu.setStyleName("databaseMenu",false);
		headerMenu.setStyleName("aboutUsMenu",false);

		/*******************************************************************/

		//Defines the Panel for Menu Sidebar
		splitLayoutPanel.add(filterTreeForMap, new HTML("Filter Options"), 5);
		splitLayoutPanel.getHeaderWidget(filterTreeForMap).addStyleName("filteroptionsheader");
		splitLayoutPanel.setStyleName("sidebar",false);
	    
	    /*******************************************************************/
		
	    //Rootpanel where anything else is include
		dockLayoutPanel.addNorth(headerMenu, 3);
		dockLayoutPanel.addSouth(new HTML("South"), 5);
		dockLayoutPanel.addWest(splitLayoutPanel,20);	
		
	    /*******************************************************************/
		
		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		rootPanel.add(dockLayoutPanel);
		
		/*******************************************************************/
		// set a value for MovieCollection database if possible
		setDatabase();
		
		/*******************************************************************/
		// create map and hang it into the central panel
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				// Create and attach the chart
				worldmap = new GeoChart();
				// attatch it to the approriate panel
				dockLayoutPanel.add(worldmap);
				MapComponent.drawMap(worldmap, dataBase);
			}
		});	
		/*******************************************************************/
	}

	/**
	 * Calls on the server to send (and if neccessary parse from file) the Movie Database and assigns the received value to database.
	 * 
	 * @pre true
	 * @post database != null || alert given
	 */
	public void setDatabase() {
		GWT.log("Fetching movies");
		movieCollectionService.getMovieCollection(new AsyncCallback<MovieCollection>() {

			public void onSuccess(MovieCollection result){
				GWT.log("fetched "+result.getMovies().size()+" movies");
				dataBase = result;
				MapComponent.drawMap(worldmap, dataBase);
//				TableComponent.draw(movieTable, dataBase);
			}

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Failed to load movies");
				Window.alert("Failed to load movies");
			}

		});
	}
	
	//create HomeMenu, remove the current center, add HomeMenu to center
	public void setHomeMenu(final DockLayoutPanel dockLayoutPanel){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				worldmap = new GeoChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(worldmap);
				MapComponent.drawMap(worldmap, dataBase);
			}
		});	
	}
	
	//create Table, remove the current center, add Table to center
	public void setTable(final DockLayoutPanel dockLayoutPanel){
		ChartLoader tableLoader = new ChartLoader(ChartPackage.TABLE);
		tableLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				movieTable = new Table();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(movieTable);
				TableComponent.draw(movieTable, dataBase);
			}
		});
	}
	
	//create Pie Chart, remove the current center, add Pie Chart to center
	public void setPieChart(final DockLayoutPanel dockLayoutPanel){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				pieChart = new PieChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(pieChart);
				PieChartComponent.drawPieChart(pieChart, dataBase);
			}
		});	
	}
	
	//create Column Chart, remove the current center, add Column Chart to center
	public void setColumnChart(final DockLayoutPanel dockLayoutPanel){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				columnChart = new ColumnChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(columnChart);
				ColumnChartComponent.drawColumnChart(columnChart, dataBase);
			}
		});	
	}
	
	//Filters the Worldmap with the selected country
	public void changeVisibleCountriesForMap(final DockLayoutPanel dockLayoutPanel, final String country){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				worldmap = new GeoChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(worldmap);
				MapComponent.drawMap(worldmap, dataBase.filterByCountry(country));
			}
		});	
	}
	
	//Filters the Pie Chart View with the selected country
	public void changeVisibleCountriesForPieChart(final DockLayoutPanel dockLayoutPanel, final String country){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				pieChart = new PieChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(pieChart);
				PieChartComponent.drawPieChart(pieChart, dataBase.filterByCountry(country));
			}
		});	
	}
	
	//Filters the Table View with the selected country
	public void changeVisibleCountriesForTable(final DockLayoutPanel dockLayoutPanel, final String country){
		ChartLoader tableLoader = new ChartLoader(ChartPackage.TABLE);
		tableLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				movieTable = new Table();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(movieTable);
				TableComponent.draw(movieTable, dataBase.filterByCountry(country));
			}
		});	
	}
	
	//Filters the Bar Diagram View with the selected country
	public void changeVisibleCountriesForBarDiagram(final DockLayoutPanel dockLayoutPanel, final String country){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				columnChart = new ColumnChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(columnChart);
				ColumnChartComponent.drawColumnChart(columnChart, dataBase.filterByCountry(country));
			}
		});	
	}
	
	public void changeVisibleLanguageForMap(final DockLayoutPanel dockLayoutPanel, final String language){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				worldmap = new GeoChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(worldmap);
				MapComponent.drawMap(worldmap, dataBase.filterByLanguage(language));
			}
		});	
	}
	
	public void changeVisibleLanguageForTableChart(final DockLayoutPanel dockLayoutPanel, final String language){
		ChartLoader tableLoader = new ChartLoader(ChartPackage.TABLE);
		tableLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				movieTable = new Table();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(movieTable);
				TableComponent.draw(movieTable, dataBase.filterByLanguage(language));
			}
		});	
	}
	
	public void changeVisibleLanguageForPieChart(final DockLayoutPanel dockLayoutPanel, final String language){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				pieChart = new PieChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(pieChart);
				PieChartComponent.drawPieChart(pieChart, dataBase.filterByCountry(language));
			}
		});	
	}
	
	public void changeVisibleLanguageForBarDiagram(final DockLayoutPanel dockLayoutPanel, final String language){
		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {
			@Override
			public void run() {
				columnChart = new ColumnChart();
				dockLayoutPanel.remove(3);
				dockLayoutPanel.add(columnChart);
				ColumnChartComponent.drawColumnChart(columnChart, dataBase.filterByCountry(language));
			}
		});	
	}
	
	//Delets the chosen filter depending on the current Center
	public void deleteFilter(final DockLayoutPanel dockLayoutPanel,GeoChart geoChart,PieChart piesChart,Table moviesTable, ColumnChart column){
		if(dockLayoutPanel.getWidget(3) == geoChart){
			ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
			chartLoader.loadApi(new Runnable() {
				@Override
				public void run() {
					worldmap = new GeoChart();
					dockLayoutPanel.remove(3);
					dockLayoutPanel.add(worldmap);
					MapComponent.drawMap(worldmap, dataBase);
				}
			});	
		}
		if(dockLayoutPanel.getWidget(3) == moviesTable){
			ChartLoader tableLoader = new ChartLoader(ChartPackage.TABLE);
			tableLoader.loadApi(new Runnable() {
				@Override
				public void run() {
					movieTable = new Table();
					dockLayoutPanel.remove(3);
					dockLayoutPanel.add(movieTable);
					TableComponent.draw(movieTable, dataBase);
				}
			});
		}
		if(dockLayoutPanel.getWidget(3) == piesChart){
			ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
			chartLoader.loadApi(new Runnable() {
				@Override
				public void run() {
					pieChart = new PieChart();
					dockLayoutPanel.remove(3);
					dockLayoutPanel.add(pieChart);
					PieChartComponent.drawPieChart(pieChart, dataBase);
				}
			});	
		}
		if(dockLayoutPanel.getWidget(3) == column){
			ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
			chartLoader.loadApi(new Runnable() {
				@Override
				public void run() {
					columnChart = new ColumnChart();
					dockLayoutPanel.remove(3);
					dockLayoutPanel.add(columnChart);
					ColumnChartComponent.drawColumnChart(columnChart, dataBase);
				}
			});	
		}
		}
}

