package amdb.client;

import amdb.shared.MovieCollection;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Command;
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
	private PushButton updateCountry = new PushButton("Update Chart");
	private PushButton updateLanguage = new PushButton("Update Chart");
	private PushButton updateGenre = new PushButton("Update Chart");
	private PushButton updateMinLength = new PushButton("Update Chart");
	private PushButton delete = new PushButton("Delete the chosen filter");
	private Tree filterTreeForMap = new Tree();
	private MovieCollection dataBase; // should not be changed
	private MovieCollection currentMovies;

	private GeoChart worldmap;
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
		filterTreeForMap.addItem(countrySort);
		filterTreeForMap.addItem(languageSort);
		filterTreeForMap.addItem(genreSort);
		filterTreeForMap.addItem(minLengthSort);
		filterTreeForMap.addItem(delete);
		filterTreeForMap.addItem(exportButtonSort);
		
		countrySort.setStyleName("countrySort",false);
		languageSort.setStyleName("languageSort",false);
		genreSort.setStyleName("genreSort",false);
		minLengthSort.setStyleName("minLengthSort",false);
		exportButtonSort.setStyleName("exportButtonSort",false);
		
		//All clickevents for the buttons in the sidebar
		
		delete.addClickHandler(new ClickHandler(){
	    	public void onClick(ClickEvent event) {
	    		deleteFilter(dockLayoutPanel,worldmap,pieChart,movieTable,columnChart);
	    	}
	    });
		
		updateCountry.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateCountryChart(dockLayoutPanel,worldmap,pieChart,movieTable,columnChart,listBoxForCountries.getSelectedItemText());
			}
		});
		
		updateLanguage.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateLanguageChart(dockLayoutPanel,worldmap,pieChart,movieTable,columnChart,listBoxForLanguages.getSelectedItemText());
			}
		});
		
		updateGenre.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateGenreChart(dockLayoutPanel,worldmap,pieChart,movieTable,columnChart,listBoxForGenres.getSelectedItemText());
			}
		});
		
		updateMinLength.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				updateMinLengthChart(dockLayoutPanel,worldmap,pieChart,movieTable,columnChart,textBoxForMinLength.getValue());
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
		MenuBar informationBackground = new MenuBar(true);
		headerMenu.addItem("Home/Worldmap",homeMenu);
		headerMenu.addItem("Pie Chart", pieChartViewMenu);
		headerMenu.addItem("Table", tableViewMenu);
		headerMenu.addItem("Bar Diagram",barDiagramViewMenu);
		headerMenu.addItem("About Us", aboutUsMenu);
		headerMenu.addItem("Where our informations come from", informationBackground);
		
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
		dockLayoutPanel.addSouth(new HTML("South"), 4);
		dockLayoutPanel.addEast(new HTML("East"), 7);
		dockLayoutPanel.addWest(splitLayoutPanel,20);	
		
	    /*******************************************************************/
		
		RootLayoutPanel rootPanel = RootLayoutPanel.get();
		rootPanel.add(dockLayoutPanel);
		
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
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "PreprocessedData/movies_preprocessed.tsv");
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
	 */
	public void onDatabaseReady(){
		
		countries = dataBase.getAllCountries();
		languages = dataBase.getAllLanguages();
		genres = dataBase.getAllGenres();
		
		//Fill the two listBoxes for the sidebar
		for(int i = 0; i < countries.length; i++){
			listBoxForCountries.addItem(countries[i]);
		}
		for(int i = 0; i < languages.length; i++){
			listBoxForLanguages.addItem(languages[i]);
		}
		
		for (int i = 0; i < genres.length; i++) {
			listBoxForGenres.addItem(genres[i]);
		}
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
		
	//Deletes the chosen filter depending on the current Center
	public void deleteFilter(final DockLayoutPanel dockLayoutPanel,GeoChart geoChart,PieChart piesChart,Table moviesTable, ColumnChart column){
		currentMovies = dataBase;
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
			tableLoader.loadApi(new Runnable(){
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
	//Update the chosen filter depending on the current Center
	public void updateCountryChart(final DockLayoutPanel dockLayoutPanel,GeoChart geoChart,PieChart piesChart,Table moviesTable, ColumnChart column,final String country){
		if(dockLayoutPanel.getWidget(3) == geoChart){
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
		if(dockLayoutPanel.getWidget(3) == moviesTable){
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
		if(dockLayoutPanel.getWidget(3) == piesChart){
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
		if(dockLayoutPanel.getWidget(3) == column){
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
	}
	
	//Update the chosen filter depending on the current Center
		public void updateLanguageChart(final DockLayoutPanel dockLayoutPanel,GeoChart geoChart,PieChart piesChart,Table moviesTable, ColumnChart column,final String language){
			if(dockLayoutPanel.getWidget(3) == geoChart){
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
			if(dockLayoutPanel.getWidget(3) == moviesTable){
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
			if(dockLayoutPanel.getWidget(3) == piesChart){
				ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
				chartLoader.loadApi(new Runnable() {
					@Override
					public void run() {
						pieChart = new PieChart();
						dockLayoutPanel.remove(3);
						dockLayoutPanel.add(pieChart);
						PieChartComponent.drawPieChart(pieChart, dataBase.filterByLanguage(language));
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
						ColumnChartComponent.drawColumnChart(columnChart, dataBase.filterByLanguage(language));
					}
				});	
			}
		}
		
		public void updateGenreChart(final DockLayoutPanel dockLayoutPanel,GeoChart geoChart,PieChart piesChart,Table moviesTable, ColumnChart column,final String genre){
			if(dockLayoutPanel.getWidget(3) == geoChart){
				ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
				chartLoader.loadApi(new Runnable() {
					@Override
					public void run() {
						worldmap = new GeoChart();
						dockLayoutPanel.remove(3);
						dockLayoutPanel.add(worldmap);
						MapComponent.drawMap(worldmap, dataBase.filterByGenre(genre));
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
						TableComponent.draw(movieTable, dataBase.filterByGenre(genre));
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
						PieChartComponent.drawPieChart(pieChart, dataBase.filterByGenre(genre));
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
						ColumnChartComponent.drawColumnChart(columnChart, dataBase.filterByGenre(genre));
					}
				});	
			}
		}
		
		public void updateMinLengthChart(final DockLayoutPanel dockLayoutPanel,GeoChart geoChart,PieChart piesChart,Table moviesTable, ColumnChart column,final String minLength){
			if(dockLayoutPanel.getWidget(3) == geoChart){
				ChartLoader chartLoader = new ChartLoader(ChartPackage.GEOCHART);
				chartLoader.loadApi(new Runnable() {
					@Override
					public void run() {
						worldmap = new GeoChart();
						dockLayoutPanel.remove(3);
						dockLayoutPanel.add(worldmap);
						int intMinLength = Integer.parseInt(minLength);
						MapComponent.drawMap(worldmap, dataBase.filterByMinLength(intMinLength));
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
						int intMinLength = Integer.parseInt(minLength);
						TableComponent.draw(movieTable, dataBase.filterByMinLength(intMinLength));
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
						int intMinLength = Integer.parseInt(minLength);
						PieChartComponent.drawPieChart(pieChart, dataBase.filterByMinLength(intMinLength));
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
						int intMinLength = Integer.parseInt(minLength);
						ColumnChartComponent.drawColumnChart(columnChart, dataBase.filterByMinLength(intMinLength));
					}
				});	
			}
		}
}

