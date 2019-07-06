/*
Copyright (c) 2003-2019, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.md or https://ckeditor.com/legal/ckeditor-oss-license
*/

.cke_balloon.cke_balloontoolbar
{
	border: 1px solid #D3D3D3;
	background: #fff url(../../../../skins/kama/images/sprites.png) repeat-x 0 -150px;
	background: linear-gradient(to bottom, #fff, #d3d3d3 20px);
	padding: 0;
}

.cke_balloon.cke_balloontoolbar .cke_balloon_content
{
	padding: 4px;
}

/* side: [ bottom, top ] */
.cke_balloon.cke_balloontoolbar .cke_balloon_triangle_outer.cke_balloon_triangle_bottom,
.cke_balloon.cke_balloontoolbar .cke_balloon_triangle_outer.cke_balloon_triangle_top,
.cke_balloon.cke_balloontoolbar .cke_balloon_triangle_inner.cke_balloon_triangle_bottom
{
	border-color: #D3D3D3 transparent;
}

.cke_balloon.cke_balloontoolbar .cke_balloon_triangle_inner.cke_balloon_triangle_top
{
	border-color: #fff transparent;
}
