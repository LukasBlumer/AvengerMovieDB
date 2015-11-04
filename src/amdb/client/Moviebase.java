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
<<<<<<< HEAD
import com.google.gwt.user.client.ui.VerticalPanel;

import amdb.shared.MovieCollection;
=======
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.geochart.GeoChart;
>>>>>>> origin/master

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Moviebase implements EntryPoint {

	final StackLayoutPanel slp = new StackLayoutPanel(Unit.EM);
	final StackLayoutPanel slp2 = new StackLayoutPanel(Unit.EM);
	private PushButton export = new PushButton("Export this view");
	private Button home = new Button("Home");
	private Button Database = new Button("Database");
	private Button About = new Button("About");
	private HorizontalPanel headerPanel = new HorizontalPanel();
	private HorizontalPanel southPanel = new HorizontalPanel();
	private VerticalPanel verticalPanel	= new VerticalPanel();
	private Tree filterTree = new Tree();
	private Tree worldmapTree = new Tree();
	private MovieCollection dataBase; // should not be changed
	private GeoChart worldmap;

	private final MovieCollectionServiceInterfaceAsync movieCollectionService = GWT.create(MovieCollectionServiceInterface.class);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		/*******************************************************************/

		//Builds the Tree for the Global Sort Options part
		TreeItem sort1 = new TreeItem(new CheckBox("Nach Jahr filtern"));
		TreeItem sort2 = new TreeItem(new CheckBox("Nach Land filtern"));
		TreeItem sort3 = new TreeItem(new CheckBox("Nach Sprache filtern"));
		TreeItem exportButtonSort = new TreeItem(new PushButton("Export this view"));

		filterTree.addItem(sort1);
		filterTree.addItem(sort2);
		filterTree.addItem(sort3);
<<<<<<< HEAD
		filterTree.addItem(exportButtonSort);
		
		sort1.setStyleName("sort1",false);
		sort2.setStyleName("sort2",false);
		sort3.setStyleName("sort3",false);
		exportButtonSort.setStyleName("exportButtonSort",false);
		
		ListBox sortListBox = new ListBox();
	    MovieCollection movieCollection = new MovieCollection();
	    int startYear = movieCollection.getMinYear();
	    int endYear = movieCollection.getMaxYear();
	    for(int i = startYear ; i <= endYear ; i++){
	    	sortListBox.addItem(Integer.toString(i));
	    }
	    sortListBox.setVisibleItemCount(1);
	    filterTree.add(sortListBox);
=======

		sort1.setStyleName("sort1",false);
		sort2.setStyleName("sort2",false);
		sort3.setStyleName("sort3",false);

>>>>>>> origin/master
		/*******************************************************************/

		//Builds the Tree for the Worldmap Sort Options part
	    TreeItem wmsort1 = new TreeItem(new CheckBox("Nach Jahr filtern"));
		TreeItem wmsort2 = new TreeItem(new CheckBox("Nach Land filtern"));
		TreeItem wmsort3 = new TreeItem(new CheckBox("Nach Sprache filtern"));
		TreeItem wmExportButton = new TreeItem(new PushButton("Export this view"));

		worldmapTree.addItem(wmsort1);
		worldmapTree.addItem(wmsort2);
		worldmapTree.addItem(wmsort3);
<<<<<<< HEAD
		worldmapTree.addItem(wmExportButton);
		
		wmsort1.setStyleName("wmsort1",false);
		wmsort2.setStyleName("wmsort2",false);
		wmsort3.setStyleName("wmsort3",false);
		wmExportButton.setStyleName("wmExportButton", false);
		
		
		
=======

		wmsort1.setStyleName("wmsort1",false);
		wmsort2.setStyleName("wmsort2",false);
		wmsort3.setStyleName("wmsort3",false);

>>>>>>> origin/master
		/*******************************************************************/

		//Build up the HeaderPanel
		//headerPanel.add(minimize);
		//headerPanel.add(maximize);
		headerPanel.add(home);
		headerPanel.add(Database);
		headerPanel.add(About);
		headerPanel.add(export);

		/*******************************************************************/

		Command cmd = new Command() {
			public void execute() {
				Window.alert("You selected a menu item!");
			}
		};

		//Menu Bar for the header and names for stylechanges
		MenuBar headerMenu = new MenuBar();
		MenuBar homeMenu = new MenuBar(true);
		MenuBar databaseMenu = new MenuBar(true);
		MenuBar aboutUsMenu	= new MenuBar(true);
		headerMenu.addItem("Home",homeMenu);
		headerMenu.addItem("Database",databaseMenu);
		headerMenu.addItem("About Us", aboutUsMenu);


		headerMenu.setStyleName("headerMenu",false);
		headerMenu.setStyleName("homeMenu",false);
		headerMenu.setStyleName("databaseMenu",false);
		headerMenu.setStyleName("aboutUsMenu",false);

		/*******************************************************************/

		//Defines the Panel for Menu Sidebar
		slp.add(filterTree, new HTML("Filter Options"), 10);
<<<<<<< HEAD
	    slp.add(worldmapTree, new HTML("Worldmap View"), 10);
	    slp.add(new HTML("Table view"), new HTML("Table View"), 10);
	    slp.add(new HTML("Pie Chart Button"), new HTML("Pie Chart View"), 10);
	    slp.add(new HTML("Bar Diagram button"), new HTML("Bar Diagram View"), 10);
	    slp.getHeaderWidget(filterTree).addStyleName("filteroptionsheader");
	    slp.setStyleName("sidebar",false);
	    
	    /*******************************************************************/
		
	    //Rootpanel where anything else is include
		DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		p.addNorth(headerMenu, 3);
		p.addSouth(new HTML("South"), 5);
		p.addWest(slp,20);
        p.add(new HTML("Center"));
	    
	    /*******************************************************************/
	    
	    //builds the rootPanel where all the other widgets and panels are included
	  /*SplitLayoutPanel sl = new SplitLayoutPanel();
	    sl.addNorth(headerMenu, 40);
	    sl.insertSouth(new HTML("south"), 2, null);
	    sl.insertWest(slp, 18, null);
	    sl.setWidgetMinSize(headerMenu, 40);
	    sl.add(new HTML("center"));*/
		
		RootLayoutPanel rp = RootLayoutPanel.get();
		//rp.add(sl);
		rp.add(p);
		
		/*******************************************************************/
	
	}
	}
=======
		slp.add(worldmapTree, new HTML("Worldmap View"), 10);
		slp.add(new HTML("Table view"), new HTML("Table View"), 10);
		slp.add(new HTML("Pie Chart Button"), new HTML("Pie Chart View"), 10);
		slp.add(new HTML("Bar Diagram button"), new HTML("Bar Diagram View"), 10);
		slp.getHeaderWidget(filterTree).addStyleName("filteroptionsheader");
		slp.setStyleName("sidebar",false);

		/*******************************************************************/

		/*Rootpanel where anything else is include
		DockLayoutPanel p = new DockLayoutPanel(Unit.EM);
		p.addNorth(headerPanel, 30);
		p.insertSouth(new HTML("south"), 2,null);
		p.insertEast(new HTML("east"), 2,null);
		p.addWest(slp,18);
        p.add(new HTML("center"));*/

		/*******************************************************************/

		//builds the rootPanel where all the other widgets and panels are included
		final SplitLayoutPanel sl = new SplitLayoutPanel();
		sl.addNorth(headerMenu, 40);
		sl.insertSouth(new HTML("south"), 2, null);
		sl.insertWest(slp, 18, null);
		sl.setWidgetMinSize(headerMenu, 40);
//		sl.add(new HTML("center"));

		RootLayoutPanel rp = RootLayoutPanel.get();
		rp.add(sl);
		//rp.add(p);

		/*******************************************************************/

		//functions for the minimize and the maximize buttons
		minimize.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				slp.setVisible(false);
			}
		});

		maximize.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				slp.setVisible(true);
			}
		});

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
				sl.add(worldmap);
//				Map.drawMap(worldmap, dataBase);
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
	public void setDatabase(){
		movieCollectionService.getMovieCollection(new AsyncCallback<MovieCollection>() {

			public void onSuccess(MovieCollection result){
				dataBase = result;
				Map.drawMap(worldmap, dataBase);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to load movies");
			}

		});
	}

}
>>>>>>> origin/master
