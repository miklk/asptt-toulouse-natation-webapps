function familles(id, data) {
	var width = 350,
	    height = 350,
	    radius = Math.min(width, height) / 2;

	var color = d3.scale.ordinal()
	    .range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);

	var arc = d3.svg.arc()
	    .outerRadius(radius - 10)
	    .innerRadius(0);

	var pie = d3.layout.pie()
	    .sort(null)
	    .value(function(d) { return d.second; });

	var svg = d3.select(id).append("svg")
	    .attr("width", width)
	    .attr("height", height)
	  .append("g")
	    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");


	  var g = svg.selectAll(".arc")
	      .data(pie(data))
	    .enter().append("g")
	      .attr("class", "arc");

	  g.append("path")
	      .attr("d", arc)
	      .style("fill", function(d) { return color(d.data.first); });

	  g.append("svg:text")
      .attr("transform", function(d) { //set the label's origin to the center of the arc
        //we have to make sure to set these before calling arc.centroid
        d.outerRadius = radius + 50; // Set Outer Coordinate
        d.innerRadius = radius + 45; // Set Inner Coordinate
        return "translate(" + arc.centroid(d) + ")";
      })
      .attr("text-anchor", "middle") //center the text on it's origin
      .style("fill", "Purple")
      .style("font", "bold 12px Arial")
      .text(function(d, i) { return d.data.first; });
	 
	  
	  function legend(lD){
	        var leg = {};
	            
	        // create table for legend.
	        var legend = d3.select(id).append("table").attr('class','legend');
	        
	        // create one row per segment.
	        var tr = legend.append("tbody").selectAll("tr").data(lD).enter().append("tr");
	            
	        // create the first column for each segment.
	        tr.append("td").append("svg").attr("width", '16').attr("height", '16').append("rect")
	            .attr("width", '16').attr("height", '16')
				.attr("fill",function(d){ return color(d.first); });
	            
	        // create the second column for each segment.
	        tr.append("td").text(function(d){ return d.first;});

	        // create the third column for each segment.
	        tr.append("td").attr("class",'legendFreq')
	            .text(function(d){ return d3.format(",")(d.second);});

	        // create the fourth column for each segment.
	        tr.append("td").attr("class",'legendPerc')
	            .text(function(d){ return getLegend(d,lD);});

	        // Utility function to be used to update the legend.
	        leg.update = function(nD){
	            // update the data attached to the row elements.
	            var l = legend.select("tbody").selectAll("tr").data(nD);

	            // update the frequencies.
	            l.select(".legendFreq").text(function(d){ return d3.format(",")(d.second);});

	            // update the percentage column.
	            l.select(".legendPerc").text(function(d){ return getLegend(d,nD);});        
	        }
	        
	        function getLegend(d,aD){ // Utility function to compute percentage.
	            return d3.format("%")(d.second/d3.sum(aD.map(function(v){ return v.second; })));
	        }

	        return leg;
	    };
	    leg = legend(data);


}

function famillesGroupe(id, data) {
	var margin = {top: 20, right: 20, bottom: 30, left: 40},
    width = 960 - margin.left - margin.right,
    height = 500 - margin.top - margin.bottom;

var x = d3.scale.ordinal()
    .rangeRoundBands([0, width], .1);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var svg = d3.select(id).append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", 700)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
    
    

  x.domain(data.map(function(d) { return d.first; }));
  y.domain([0, d3.max(data, function(d) { return d.second; })]);

  svg.append("g")
      .attr("class", "x axis-famille-groupe")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis)
      .selectAll("text")  
            .style("text-anchor", "end")
            .attr("dx", "-.8em")
            .attr("dy", ".15em")
            .attr("transform", function(d) {
                return "rotate(-65)" 
                });

  svg.append("g")
      .attr("class", "y axis-famille-groupe")
      .call(yAxis)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Nombre d'adhérents");

  svg.selectAll(".bar")
      .data(data)
    .enter().append("rect")
      .attr("class", "bar")
      .attr("x", function(d) { return x(d.first); })
      .attr("width", x.rangeBand())
      .attr("y", function(d) { return y(d.second); })
      .attr("height", function(d) { return height - y(d.second); });

function type(d) {
	  d.second = +d.second;
	  return d;
	}
}