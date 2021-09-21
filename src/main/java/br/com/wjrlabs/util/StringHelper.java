package br.com.wjrlabs.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StringHelper {

    public static final String LINE_BREAK = System.getProperty( "line.separator" );

    public static final String EMPTY      = "";

    public static String wrap( String text,
                               int screenWidth ) {
        return wrap( text,
                     screenWidth,
                     0,
                     0,
                     LINE_BREAK,
                     false );
    }

    public static String wrap( String text,
                               int screenWidth,
                               int indent ) {
        return wrap( text,
                     screenWidth,
                     indent,
                     indent,
                     LINE_BREAK,
                     false );
    }

    public static String wrap( String text,
                               int screenWidth,
                               int firstIndent,
                               int indent ) {
        return wrap( text,
                     screenWidth,
                     firstIndent,
                     indent,
                     LINE_BREAK,
                     false );
    }

    public static String wrap( String text,
                               int screenWidth,
                               int firstIndent,
                               int indent,
                               String lineBreak ) {
        return wrap( text,
                     screenWidth,
                     firstIndent,
                     indent,
                     lineBreak,
                     false );
    }

    public static String wrap( String text,
                               int screenWidth,
                               int firstIndent,
                               int indent,
                               String lineBreak,
                               boolean traceMode ) {
        return wrap( new StringBuffer( text ),
                     screenWidth,
                     firstIndent,
                     indent,
                     lineBreak,
                     traceMode ).toString( );
    }

    public static String wrap( String text,
                               int screenWidth,
                               int indent,
                               String lineBreak ) {
        return wrap( text,
                     screenWidth,
                     indent,
                     indent,
                     lineBreak,
                     false );
    }

    public static String wrap( String text,
                               int screenWidth,
                               String lineBreak ) {
        return wrap( text,
                     screenWidth,
                     0,
                     0,
                     lineBreak,
                     false );
    }

    public static StringBuffer wrap( StringBuffer text,
                                     int screenWidth,
                                     int firstIndent,
                                     int indent ) {
        return wrap( text,
                     screenWidth,
                     firstIndent,
                     indent,
                     LINE_BREAK,
                     false );
    }

    protected static StringBuffer wrap( StringBuffer text,
                                        int screenWidth,
                                        int firstIndent,
                                        int indent,
                                        String lineBreak,
                                        boolean traceMode ) {
        if ( firstIndent < 0
             || indent < 0
             || screenWidth < 0 ) {
            throw new IllegalArgumentException( "Negative dimension" );
        }
        int allowedCols = screenWidth - 1;
        if ( ( allowedCols - indent ) < 2
             || ( allowedCols - firstIndent ) < 2 ) {
            throw new IllegalArgumentException( "Usable columns < 2" );
        }
        int ln = text.length( );
        int defaultNextLeft = allowedCols
                              - indent;
        int b = 0;
        int e = 0;
        StringBuffer res = new StringBuffer( (int) ( ln * 1.2 ) );
        int left = allowedCols
                   - firstIndent;
        for ( int i = 0; i < firstIndent; i++ ) {
            res.append( ' ' );
        }
        StringBuffer tempb = new StringBuffer( indent + 2 );
        tempb.append( lineBreak );
        for ( int i = 0; i < indent; i++ ) {
            tempb.append( ' ' );
        }
        String defaultBreakAndIndent = tempb.toString( );
        boolean firstSectOfSrcLine = true;
        boolean firstWordOfSrcLine = true;
        int traceLineState = 0;
        int nextLeft = defaultNextLeft;
        String breakAndIndent = defaultBreakAndIndent;
        int wln = 0, x;
        char c, c2;
        do {
            word:
            while ( e <= ln ) {
                if ( e != ln ) {
                    c = text.charAt( e );
                }
                else {
                    c = ' ';
                }
                if ( traceLineState > 0
                     && e > b ) {
                    if ( c == '.'
                         && traceLineState == 1 ) {
                        c = ' ';
                    }
                    else {
                        c2 = text.charAt( e - 1 );
                        if ( c2 == ':' ) {
                            c = ' ';
                        }
                        else if ( c2 == '(' ) {
                            traceLineState = 2;
                            c = ' ';
                        }
                    }
                }
                if ( c != ' '
                     && c != '\n'
                     && c != '\r'
                     && c != '\t' ) {
                    e++;
                }
                else {
                    wln = e
                          - b;
                    if ( left >= wln ) {
                        res.append( text.substring( b,
                                                    e ) );
                        left -= wln;
                        b = e;
                    }
                    else {
                        wln = e
                              - b;
                        if ( wln > nextLeft
                             || firstWordOfSrcLine ) {
                            int ob = b;
                            while ( wln > left ) {
                                if ( left > 2
                                     || ( left == 2 && ( firstWordOfSrcLine || !( b == ob && nextLeft > 2 ) ) ) ) {
                                    res.append( text.substring( b,
                                                                b
                                                                                + left
                                                                                - 1 ) );
                                    res.append( "-" );
                                    res.append( breakAndIndent );
                                    wln -= left - 1;
                                    b += left - 1;
                                    left = nextLeft;
                                }
                                else {
                                    x = res.length( ) - 1;
                                    if ( x >= 0
                                         && res.charAt( x ) == ' ' ) {
                                        res.delete( x,
                                                    x + 1 );
                                    }
                                    res.append( breakAndIndent );
                                    left = nextLeft;
                                }
                            }
                            res.append( text.substring( b,
                                                        b
                                                                        + wln ) );
                            b += wln;
                            left -= wln;
                        }
                        else {
                            x = res.length( ) - 1;
                            if ( x >= 0
                                 && res.charAt( x ) == ' ' ) {
                                res.delete( x,
                                            x + 1 );
                            }
                            res.append( breakAndIndent );
                            res.append( text.substring( b,
                                                        e ) );
                            left = nextLeft
                                   - wln;
                            b = e;
                        }
                    }
                    firstSectOfSrcLine = false;
                    firstWordOfSrcLine = false;
                    break word;
                }
            }
            int extra = 0;
            space:
            while ( e < ln ) {
                c = text.charAt( e );
                if ( c == ' ' ) {
                    e++;
                }
                else if ( c == '\t' ) {
                    e++;
                    extra += 7;
                }
                else if ( c == '\n'
                          || c == '\r' ) {
                    nextLeft = defaultNextLeft;
                    breakAndIndent = defaultBreakAndIndent;
                    res.append( breakAndIndent );
                    e++;
                    if ( e < ln ) {
                        c2 = text.charAt( e );
                        if ( ( c2 == '\n' || c2 == '\r' )
                             && c != c2 ) {
                            e++;
                        }
                    }
                    left = nextLeft;
                    b = e;
                    firstSectOfSrcLine = true;
                    firstWordOfSrcLine = true;
                    traceLineState = 0;
                }
                else {
                    wln = e
                          - b
                          + extra;
                    if ( firstSectOfSrcLine ) {
                        int y = allowedCols
                                - indent
                                - wln;
                        if ( traceMode
                             && ln > e + 2
                             && text.charAt( e ) == 'a'
                             && text.charAt( e + 1 ) == 't'
                             && text.charAt( e + 2 ) == ' ' ) {
                            if ( y > 5 + 3 ) {
                                y -= 3;
                            }
                            traceLineState = 1;
                        }
                        if ( y > 5 ) {
                            y = allowedCols
                                - y;
                            nextLeft = allowedCols
                                       - y;
                            tempb = new StringBuffer( indent + 2 );
                            tempb.append( lineBreak );
                            for ( int i = 0; i < y; i++ ) {
                                tempb.append( ' ' );
                            }
                            breakAndIndent = tempb.toString( );
                        }
                    }
                    if ( wln <= left ) {
                        res.append( text.substring( b,
                                                    e ) );
                        left -= wln;
                        b = e;
                    }
                    else {
                        res.append( breakAndIndent );
                        left = nextLeft;
                        b = e;
                    }
                    firstSectOfSrcLine = false;
                    break space;
                }
            }
        }
        while ( e < ln );
        return res;
    }

    public static String wrapTrace( String text,
                                    int screenWidth ) {
        return wrap( text,
                     screenWidth,
                     0,
                     0,
                     LINE_BREAK,
                     true );
    }

    public static String repeatedCharString( char charToRepeat,
                                             int timesToRepeat ) {
        byte[ ] tmpByteBuffer = new byte[ timesToRepeat ];
        Arrays.fill( tmpByteBuffer,
                     (byte) charToRepeat );
        return new String( tmpByteBuffer );
    }

    public static String escapeFormatString( String string ) {
        String tmpFormat = string.replaceAll( "\\\\",
                                              "\\\\" );
        tmpFormat = tmpFormat.replaceAll( "%",
                                          "%%" );
        return tmpFormat;
    }

    public static String escapeRegexString( String string ) {
        String tmpRegex = string.replaceAll( "\\\\",
                                             "\\\\" );
        tmpRegex = tmpRegex.replaceAll( "\\.",
                                        "\\." );
        tmpRegex = tmpRegex.replaceAll( "\\*",
                                        "\\*" );
        tmpRegex = tmpRegex.replaceAll( "\\?",
                                        "\\?" );
        return tmpRegex;
    }

    public static String escapeHtmlString( String string ) {
        StringBuffer tmpResult = new StringBuffer( string.length( ) );
        boolean tmpLeftSpace = false;
        int tmpTextLength = string.length( );
        char tmpTextChar;
        for ( int tmpIndex = 0; tmpIndex < tmpTextLength; tmpIndex++ ) {
            tmpTextChar = string.charAt( tmpIndex );
            if ( tmpTextChar == ' ' ) {
                tmpResult.append( tmpLeftSpace
                                              ? "&nbsp;"
                                              : " " );
                tmpLeftSpace = true;
            }
            else {
                tmpLeftSpace = false;
                if ( tmpTextChar == '"' ) {
                    tmpResult.append( "&quot;" );
                }
                else if ( tmpTextChar == '&' ) {
                    tmpResult.append( "&amp;" );
                }
                else if ( tmpTextChar == '<' ) {
                    tmpResult.append( "&lt;" );
                }
                else if ( tmpTextChar == '>' ) {
                    tmpResult.append( "&gt;" );
                }
                else if ( tmpTextChar == '\n' ) {
                    tmpResult.append( "<br/>" );
                    tmpLeftSpace = true;
                }
                else {
                    int tmpUnicode = 0xffff & tmpTextChar;
                    if ( tmpUnicode < 160 )
                        tmpResult.append( tmpTextChar );
                    else {
                        tmpResult.append( "&#" );
                        tmpResult.append( new Integer( tmpUnicode ).toString( ) );
                        tmpResult.append( ';' );
                    }
                }
            }
        }
        return tmpResult.toString( );
    }

    public static String flawlessFormat( String format,
                                         Object... args ) {
        String tmpNull = "<null>";
        String tmpMessage;
        try {
            if ( format == null ) {
                tmpMessage = tmpNull;
            }
            else {
                if ( ( args == null )
                     || ( args.length == 0 ) ) {
                    tmpMessage = format;
                }
                else {
                    tmpMessage = String.format( format,
                                                args );
                }
            }
        }
        catch ( Throwable e ) {
            StringBuffer tmpArgsClasses = new StringBuffer( );
            if ( args != null ) {
                int tmpArgCount = 0;
                for ( Object tmpArg : args ) {
                    if ( tmpArgCount > 0 ) {
                        tmpArgsClasses.append( ", " );
                    }
                    tmpArgsClasses.append( ( tmpArg == null )
                                                             ? tmpNull
                                                             : tmpArg.getClass( ).getSimpleName( ) );
                    ++tmpArgCount;
                }
            }
            String tmpExceptionMessage = e.getLocalizedMessage( );
            tmpMessage = String.format( "%s em String.format( \"%s\"%s ): %s",
                                        e.getClass( ).getSimpleName( ),
                                        format,
                                        tmpArgsClasses.toString( ),
                                        ( tmpExceptionMessage != null )
                                                                       ? tmpExceptionMessage
                                                                       : tmpNull );
        }
        return tmpMessage;
    }

    public static String dumpStringList( List< String > list ) {
        return StringHelper.dumpStringList( list,
                                            "\n" );
    }

    public static String dumpStringList( List< String > list,
                                         String separator ) {
        StringBuilder tmpResult = new StringBuilder( );
        for ( String tmpDetail : list ) {
            if ( tmpResult.length( ) > 0 ) {
                tmpResult.append( separator );
            }
            tmpResult.append( tmpDetail );
        }
        return tmpResult.toString( );
    }

    public static List< String > dumpStringMap( Map< String, String > map ) {
        return StringHelper.dumpStringMap( map,
                                           ": " );
    }

    public static List< String > dumpStringMap( Map< String, String > map,
                                                String separator ) {
        List< String > tmpResult = new ArrayList< String >( );
        StringBuilder tmpStringBuilder = new StringBuilder( 100 );
        for ( Map.Entry< String, String > tmpFieldEntry : map.entrySet( ) ) {
            tmpStringBuilder.delete( 0,
                                     tmpStringBuilder.length( ) );
            tmpStringBuilder.append( tmpFieldEntry.getKey( ) );
            tmpStringBuilder.append( separator );
            tmpStringBuilder.append( tmpFieldEntry.getValue( ) );
            tmpResult.add( tmpStringBuilder.toString( ) );
        }
        return tmpResult;
    }
}
