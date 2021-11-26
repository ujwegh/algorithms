import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int TOP_NUMBER = 0;

    private final Site[][] openClosedSiteGrid;
    private final int siteGridLength;
    private final int bottomNumber;
    private final WeightedQuickUnionUF uf;
    private int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("number of sites must be greater than 0");
        }
        openClosedSiteGrid = new Site[n][n];
        initGrid(n);
        siteGridLength = n;
        bottomNumber = n * n + 1;
        // creates tree with size of all sites + bottom site + top site
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    private void initGrid(int n) {
        int number = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                openClosedSiteGrid[i][j] = new Site(number);
                number++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndexInGrid(row, col);
        openClosedSiteGrid[row - 1][col - 1].setOpen(true);
        numberOfOpenSites++;

        unionNeighbourSites(row, col);
    }

    private void unionNeighbourSites(int row, int col) {
        // current site
        Site site = getSite(row, col);
        // union top site with first site
        if (row == 1) {
            uf.union(site.getNumber(), TOP_NUMBER);
        }
        // union bottom site with last site
        if (row == siteGridLength) {
            uf.union(site.getNumber(), bottomNumber);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            Site upSite = getSite(row - 1, col);
            uf.union(site.getNumber(), upSite.getNumber());
        }
        if (row < siteGridLength && isOpen(row + 1, col)) {
            Site downSite = getSite(row + 1, col);
            uf.union(site.getNumber(), downSite.getNumber());
        }
        if (col > 1 && isOpen(row, col - 1)) {
            Site leftSite = getSite(row, col - 1);
            uf.union(site.getNumber(), leftSite.getNumber());
        }
        if (col < siteGridLength && isOpen(row, col + 1)) {
            Site rightSite = getSite(row, col + 1);
            uf.union(site.getNumber(), rightSite.getNumber());
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndexInGrid(row, col);
        return openClosedSiteGrid[row - 1][col - 1].isOpen();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndexInGrid(row, col);
        Site site = openClosedSiteGrid[row - 1][col - 1];
        return uf.find(site.getNumber()) == uf.find(TOP_NUMBER);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(TOP_NUMBER) == uf.find(bottomNumber);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private Site getSite(int row, int col) {
        return openClosedSiteGrid[row - 1][col - 1];
    }

    private void checkIndexInGrid(int row, int col) {
        if (row <= 0 || row > siteGridLength || col <= 0 || col > siteGridLength) {
            throw new IndexOutOfBoundsException();
        }
    }

    private final class Site {
        private boolean open;
        private final int number;

        private Site(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }
    }
}
