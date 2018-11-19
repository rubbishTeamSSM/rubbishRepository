/**
 * This jQuery plugin displays pagination links inside the selected elements.
 *
 * @author Gabriel Birke (birke *at* d-scribe *dot* de)
 * @version 1.2
 * @param {int} maxentries Number of entries to paginate
 * @param {Object} opts Several options (see README for documentation)
 * @return {Object} jQuery Object
 */
jQuery.fn.pagination = function(maxentries, opts){
	opts = jQuery.extend({
		items_per_page:PAGE_SIZE,
		num_display_entries:10,
		current_page:1,
		num_edge_entries:2,
		link_to:"#",
		prev_text:"Prev",
		next_text:"Next",
		ellipse_text:"...",
		prev_show_always:true,
		next_show_always:true,
		callback:function(){return false;}
	},opts||{});
	
	return this.each(function() {
		/**
		 * Calculate the maximum number of pages
		 */
		function numPages() {
			return Math.ceil(maxentries/opts.items_per_page);
		}
		
		/**
		 * Calculate start and end point of pagination links depending on 
		 * current_page and num_display_entries.
		 * @return {Array}
		 */
		function getInterval()  {
			var ne_half = Math.ceil(parseInt(opts.num_display_entries)/2);
			var np = numPages();
			var upper_limit = np-opts.num_display_entries;
			var start = current_page>ne_half?Math.max(Math.min(current_page-ne_half, upper_limit), 1):1;
			var end = current_page>ne_half?Math.min(current_page+ne_half, np):Math.min(opts.num_display_entries, np);
			return [start,end];
		}
		
		/**
		 * This is the event handling function for the pagination links. 
		 * @param {int} page_id The new page number
		 */
		function pageSelected(page_id, evt){
			current_page = page_id;
			drawLinks();
			var continuePropagation = opts.callback(page_id, panel);
			if (!continuePropagation) {
				if (evt.stopPropagation) {
					evt.stopPropagation();
				}
				else {
					evt.cancelBubble = true;
				}
			}
			return continuePropagation;
		}
		
		/**
		 * This function inserts the pagination links into the container element
		 */
		function drawLinks() {
			panel.empty();
			var interval = getInterval();
			var np = numPages();
			// This helper function returns a handler function that calls pageSelected with the right page_id
			var getClickHandler = function(page_id) {
				return function(evt){ return pageSelected(page_id,evt); };
			};
			
			/**
			 * 修改每页显示多少条
			 */
			var changeSize=function(){
				alert(123);
				/*PAGE_SIZE=$(this).val();
				alert(PAGE_SIZE);*/
				/*return function(evt){ return pageSelected(1,evt); };*/
			};
			// Helper function for generating a single link (or a span tag if it's the current page)
			var appendPageSize=function(){
				var html="<div class='pagesize'>每页显示</div>";
				html+="<select onchange='changeSize($(this))'>";
				var pageSize_List=[10,20,30];
				for(var i=0;i<pageSize_List.length;i++){
					if(pageSize_List[i]==PAGE_SIZE){
						html+="<option value='"+pageSize_List[i]+"'  selected = 'selected'>"+pageSize_List[i]+"</option>";
					}
					else{
						html+="<option value='"+pageSize_List[i]+"'>"+pageSize_List[i]+"</option>";
					}
				}
				html+="</select>";
				html+="<div class='pagesize'>条</div>";
				var lnk=jQuery(html);
				panel.append(lnk);
			};
			appendPageSize();//加载每页显示多少条
			var appendTotalNum=function(){
				var lnk=jQuery("<span class='totalSize'>共有"+maxentries+"条，当前<span id='currentPage'>"+current_page+"</span>/"+np+"页</span>");
				panel.append(lnk);
			};
			appendTotalNum();//加载总共有多少条和当前是第几页
			var appendItem = function(page_id, appendopts){
				page_id = page_id<1?1:(page_id<np?page_id:np); // Normalize page id to sane value
				appendopts = jQuery.extend({text:page_id, classes:""}, appendopts||{});
				if(page_id == current_page){
					var lnk = jQuery("<span class='current'>"+(appendopts.text)+"</span>");
				}
				else
				{
					var lnk = jQuery("<a>"+(appendopts.text)+"</a>")
						.bind("click", getClickHandler(page_id))
						.attr('href', opts.link_to.replace(/__id__/,page_id));
						
						
				}
				if(appendopts.classes){lnk.addClass(appendopts.classes);}
				panel.append(lnk);
				$("#currentPage").text(current_page);
			}
			// Generate "Previous"-Link
			if(opts.prev_text && (current_page > 0 || opts.prev_show_always)){
				appendItem(1,{text:"首页", classes:"next"});
				appendItem(current_page-1,{text:opts.prev_text, classes:"prev"});
			}
			// Generate starting points
			if (interval[0] > 0 && opts.num_edge_entries > 0)
			{
				var end = Math.min(opts.num_edge_entries, interval[0]);
				for(var i=1; i<=end; i++) {
					appendItem(i);
				}
				if(opts.num_edge_entries < interval[0] && opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
			}
			// Generate interval links
			for(var i=interval[0]+1; i<=interval[1]; i++) {
				appendItem(i);
			}
			// Generate ending points
			if (interval[1] <= np && opts.num_edge_entries > 0)
			{
				if(np-opts.num_edge_entries > interval[1]&& opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
				var begin = Math.max(np-opts.num_edge_entries, interval[1]);
				for(var i=(begin+1); i<=np; i++) {
					appendItem(i);
				}
				
			}
			// Generate "Next"-Link
			if(opts.next_text && (current_page < np-1 || opts.next_show_always)){
				appendItem(current_page+1,{text:opts.next_text, classes:"next"});
				appendItem(np,{text:"尾页", classes:"next"});
			}
		}
		
		// Extract current_page from options
		var current_page = opts.current_page;
		// Create a sane value for maxentries and items_per_page
		maxentries = (!maxentries || maxentries < 0)?1:maxentries;
		opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0)?1:opts.items_per_page;
		// Store DOM element for easy access from all inner functions
		var panel = jQuery(this);
		// Attach control functions to the DOM element 
		this.selectPage = function(page_id){ pageSelected(page_id);}
		this.prevPage = function(){ 
			if (current_page > 0) {
				pageSelected(current_page - 1);
				return true;
			}
			else {
				return false;
			}
		}
		this.nextPage = function(){ 
			if(current_page < numPages()-1) {
				pageSelected(current_page+1);
				return true;
			}
			else {
				return false;
			}
		}
		// When all initialisation is done, draw the links
		drawLinks();
        // call callback function
        opts.callback(current_page, this);
	});
}


