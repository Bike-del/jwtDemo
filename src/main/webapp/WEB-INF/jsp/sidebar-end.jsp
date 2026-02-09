<%@ page contentType="text/html;charset=UTF-8" %>

</div> <!-- admin-content -->
</div> <!-- admin-wrapper -->

<script>
    // Highlight active menu item
    (function () {
        const page = location.pathname.split("/").pop();
        document.querySelectorAll(".menu-link").forEach(link => {
            if (link.getAttribute("href").endsWith(page)) {
                link.classList.add("active");
            }
        });
    })();
</script>
