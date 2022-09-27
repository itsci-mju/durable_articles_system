<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!-- 				logo -->
				<a class="navbar-brand" href="durable"><i class="fa fa-cube"></i>
					ระบบจัดการครุภัณฑ์</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<c:if test="${sessionScope.staffSession.staff_name != null}">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-database"></i>
								ข้อมูลครุภัณฑ์<span class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<li><a href="load_importfiledurable_page"><i
										class="fa fa-file-o"></i> เพิ่มไฟล์ครุภัณฑ์</a></li>
								<!-- <li><a href="test">test</a></li> -->
								<li><a href="do_loadDurableControllPage"><i
										class="fa fa-floppy-o"></i> ทะเบียนควบคุมทรัพย์สิน </a></li>
								<li><a href="listDurableAll"><i class="fa fa-print"></i>
										ค้นหา,แก้ไข และสร้างรหัสคิวอาร์โค้ด </a></li>
								<!-- 								<li><a href="displayRecordDurable"><i -->
								<!-- 										class="fa fa-floppy-o"></i> เพิ่มรหัสครุภัณฑ์</a></li> -->
								<li><a href="do_loadlistdurablecontrolbyyear"><i
										class="fa fa-calendar" aria-hidden="true"></i>
										รายละเอียดสินทรัพย์ ประจําปีงบประมาณ</a></li>


							</ul></li>
						<!--  <li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-tags"></i> ยืม-คืนครุภัณฑ์ <span
							class="caret"></span>
					</a>
						<ul class="dropdown-menu">
							<li><a href="loadRecordborrowdurable"><i class="fa fa-tag"></i>
									ยืมครุภัณฑ์</a></li>
							<li><a href="listBorrowingByBorrowercode?method=showall"><i class="fa fa-tag"></i>
									คืนครุภัณฑ์</a></li>
							<li><a href="listBorrowingByDurablecode?method=showall"><i class="fa fa-list"></i>
									รายการยืมครุภัณฑ์</a></li>
									<li><a href="listBorrowerProfile?method=showall"><i class="fa fa-list-ol"></i>
									รายการผู้ยืมครุภัณฑ์ </a></li>
						</ul></li>-->

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="fa fa-wrench"></i>
								จัดการข้อมูลการซ่อม<span class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<li><a href="listDilapidatedDurable"><i
										class="fa fa-list-alt"></i> รายการครุภัณฑ์ที่เสียหาย</a></li>
								<!-- 								<li><a href="do_loadListMaintenance"><i
										class="fa fa-list-alt"></i> ประวัติการซ่อมบํารุง</a></li> -->
								<%-- 							<li><a href="createRepair"><i
									class="fa fa-floppy-o"></i> บันทึกข้อมูลการซ่อมครุภัณฑ์</a></li>
							<li><a href="listEditRepair"><i
									class="fa fa-pencil-square-o"></i> แก้ไขข้อมูลการซ่อมครุภัณฑ์</a></li> --%>
							</ul></li>
						<!-- 		<li><a href="load_verifyDurable"><i
								class="fa fa-check-square-o"></i> ตรวจสอบครุภัณฑ์</a></li> -->

						<c:if
							test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
							<li><a href="load_inspection_schedule"><i
									class="fa fa-calendar"></i> กำหนดวันตรวจสอบครุภัณฑ์ </a></li>
						</c:if>


						<!-- nice map load_listdurablebyyear -->
						<li><a href="do_loadListDurableByYears"><i
								class="fa fa-list-alt"></i> รายงานการตรวจสอบครุภัณฑ์ประจำปี</a></li>

						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><i class="fa fa-file-text-o"
								aria-hidden="true"></i> จัดซื้อจัดจ้าง <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<c:if
									test="${sessionScope.staffSession.staff_status == 'เจ้าหน้าที่ส่วนกลาง'}">
									<li><a href="do_loadAssignSignaturePage"><i
											class="fa fa-calendar"></i> กําหนดหน้าที่การเซ็นเอกสาร </a></li>
									<li><a href="do_loadListRequisition"><i
											class="fa fa-file-text-o" aria-hidden="true"></i> บัญชีวัสดุ
									</a></li>
								</c:if>
								<li><a href="do_loadAddPurchaseOrderRequestPage"><i
										class="fa fa-file-text-o" aria-hidden="true"></i>
										จัดทําเอกสารเสนอซื้อ/จ้าง</a></li>
								<li><a href="do_loadListDocumentPage"><i
										class="fa fa-file-text-o" aria-hidden="true"></i>
										รายการเอกสารเสนอซื้อ/จ้าง</a></li>
							</ul></li>

						<c:if test="${ sessionScope.staffSession.major.ID_Major == 999 }">
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"><i class="fa fa-file"></i> วัสดุ <span
									class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="do_loadAddItemPage">เพิ่มรายการวัสดุ</a></li>
									<li><a href="do_loadSearchItemPage">รายการวัสดุ</a></li>
								</ul></li>
						</c:if>
						<li class="dropdown navbar-left"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-user"></i> ${sessionScope.staffSession.staff_name}<span
								class="caret"></span>
						</a>

							<ul class="dropdown-menu">
								<li><a href="#">
										สาขา${sessionScope.staffSession.major.major_Name}</a></li>
								<li><a href="logout"><i class="fa fa-power-off"></i>
										ออกจากระบบ</a></li>
							</ul></li>
					</ul>
				</c:if>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
</div>
