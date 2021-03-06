<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><spring:message code="title.welcome"/></title>
	<base href="${pageContext.request.contextPath}/WEB-INF/views">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="generator" content="Mobirise v3.12.1, mobirise.com">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/twitter-icon-circle-blue-logo-preview.png" type="image/x-icon">
	<meta name="description" content="">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:700,400&amp;subset=cyrillic,latin,greek,vietnamese">
	<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css"
		  rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/animate.css/animate.min.css"
		  rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/mobirise/css/style.css"
		  rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/resources/mobirise/css/mbr-additional.css"
		  rel="stylesheet" type="text/css">
</head>
<body>
<section class="mbr-navbar mbr-navbar--freeze mbr-navbar--absolute mbr-navbar--transparent mbr-navbar--sticky mbr-navbar--auto-collapse" id="ext_menu-3">
    <div class="mbr-navbar__section mbr-section">
        <div class="mbr-section__container container">
            <div class="mbr-navbar__container">
                <div class="mbr-navbar__column mbr-navbar__column--s mbr-navbar__brand">
                    <span class="mbr-navbar__brand-link mbr-brand mbr-brand--inline">
                        <span class="mbr-brand__logo"><a href="<c:url value="/"/>"><img src="${pageContext.request.contextPath}/resources/images/twitter-icon-circle-blue-logo-preview.png" class="mbr-navbar__brand-img mbr-brand__img" alt="LikeTwitter"></a></span>
                        <span class="mbr-brand__name"><a class="mbr-brand__name text-white" href="<c:url value="/"/>">LikeTwitter</a></span>
                    </span>
                </div>
                <div class="mbr-navbar__hamburger mbr-hamburger"><span class="mbr-hamburger__line"></span></div>
                <div class="mbr-navbar__column mbr-navbar__menu">
                    <nav class="mbr-navbar__menu-box mbr-navbar__menu-box--inline-right">
                        <div class="mbr-navbar__column">
                            <ul class="mbr-navbar__items mbr-navbar__items--right float-left mbr-buttons mbr-buttons--freeze mbr-buttons--right btn-decorator mbr-buttons--active">
                                <li class="mbr-navbar__item">
                                    <a href="/?lang=ru_RU"><img src='
                                        ${pageContext.request.contextPath}/resources/images/russia-flag-16x16.png'/></a>
                                </li>
                                <li class="mbr-navbar__item">
                                    <a href="/?lang=en_EN">
                                        <img src="${pageContext.request.contextPath}/resources/images/united-kingdom-flag-16x16.png"alt="LikeTwitter"></a>
                                </li>
                                <li class="mbr-navbar__item">
                                    <a class="mbr-buttons__link btn text-white"
                                       href="<c:url value="/about"/>"><spring:message code="navbar.button.about"/>
                                    </a>
                                </li>
                                <li class="mbr-navbar__item">
                                    <a class="mbr-buttons__link btn text-white" href="<c:url
                                    value="/contact"/>"><spring:message code="navbar.button.contact"/></a>
                                </li>
                                <li class="mbr-navbar__item">
                                    <a class="mbr-buttons__link btn btn-success" href="<c:url
                                    value="/"/>"><spring:message code="navbar.button.home"/></a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</section>

<section
		class="mbr-box mbr-section mbr-section--relative mbr-section--fixed-size mbr-section--full-height mbr-section--bg-adapted mbr-parallax-background" id="header1-6" style="background-image: url(${pageContext.request.contextPath}/resources/images/mbr-2000x1333.jpg); background-size: 100%;">
	<div class="mbr-box__magnet mbr-box__magnet--sm-padding mbr-box__magnet--center-left mbr-after-navbar">
		<div class="mbr-box__container mbr-section__container container">
			<div class="mbr-box mbr-box--stretched">
				<div class="mbr-box__magnet mbr-box__magnet--center-left">
					<div class="row">
						<div class=" col-sm-6 col-sm-offset-6">
							<div class="mbr-hero animated fadeInUp">
								<h1 class="mbr-hero__text">Twitter</h1>
								<p class="mbr-hero__subtext"><spring:message code="message.welcome"/>
								</p>
							</div>
							<div class="mbr-buttons btn-inverse mbr-buttons--left">
								<a href="<c:url value="/signup"/>"  class="mbr-buttons__btn btn btn-lg btn-danger animated fadeInUp delay" ><spring:message code="button.signup"/></a>
								<a href="<c:url value="/signin"/>"
								   class="mbr-buttons__btn btn btn-lg btn-default animated fadeInUp delay" ><spring:message code="button.signin"/></a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>


<script src="${pageContext.request.contextPath}/resources/web/assets/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/smooth-scroll/smooth-scroll.js"></script>
<script src="${pageContext.request.contextPath}/resources/jarallax/jarallax.js"></script>
<script src="${pageContext.request.contextPath}/resources/mobirise/js/script.js"></script>
<script src="${pageContext.request.contextPath}/resources/formoid/formoid.min.js"></script>
</body>
</html>


